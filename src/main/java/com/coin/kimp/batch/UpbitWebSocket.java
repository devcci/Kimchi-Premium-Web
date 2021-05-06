package com.coin.kimp.batch;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coin.kimp.vo.CoinVo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

// @EnableBatchProcessing
@Component
public class UpbitWebSocket implements ApplicationRunner {
    private static final String SERVER = "wss://api.upbit.com/websocket/v1";
    DecimalFormat dFormat = new DecimalFormat("###,###");
    static Map<String, List<BigDecimal>> tradeListMap = new HashMap<String, List<BigDecimal>>();
    static Map<String, List<BigDecimal>> tradeVolumeListMap = new HashMap<String, List<BigDecimal>>();

    // 업비트 마켓 Trade WebSocket 연결
    public void getAllMarketTrade() throws Exception {
        UpbitAPI upbitAPI = new UpbitAPI();
        List<String> marketNameLst = upbitAPI.getMarketNameList();
        String marketStr = "";
        for (int i = 0; i < marketNameLst.size(); i++) {
            String marketObj = marketNameLst.get(i);
            if (i == 0) {
                marketStr = "\"" + marketObj + "\"";
            } else {
                marketStr += ",\"" + marketObj + "\"";
            }
        }
        String text = "[{\"ticket\":\"kimp\"},{\"type\":\"trade\",\"codes\":[" + marketStr + "]}]";
        OkHttpClient client = new OkHttpClient();
        WebSocketListener listener = new WebSocketListener() {
            public void onOpen(WebSocket socket, Response Response) {
                socket.send(text);
                // socket.close(1000, "end");
            }

            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("upbit websocket closed");
                System.out.println("code: " + code);
                System.out.println("reason: " + reason);
            }

            public void onMessage(WebSocket webSocket, ByteString bytes) {
                JSONParser jsonParser = new JSONParser();
                try {
                    Object obj = jsonParser.parse(bytes.utf8());
                    JSONObject jsonObject = (JSONObject) obj;
                    CoinVo coinVo = makeCoinVo(jsonObject);
                    setPriceList(coinVo);
                    setTradingVolumeList(coinVo);
                } catch (ParseException e) {
                    System.out.println("upbit socket error");
                    e.printStackTrace();
                }
            }
        };
        Request request = new Request.Builder().url(SERVER).build();
        // WebSocket socket = client.newWebSocket(request, listener);
        client.newWebSocket(request, listener);
    }

    // Trade WebSocket 데이터의 5000개 가진 리스트 유지
    private void setPriceList(CoinVo coinVo) {
        if (tradeListMap.containsKey(coinVo.getCode().toString())) {
            if (tradeListMap.get(coinVo.getCode().toString()).size() > 5000) {
                tradeListMap.get(coinVo.getCode().toString()).remove(0);
                // tradeListMap.get(coinVo.getCode().toString()).add(coinVo.getPrice());
                tradeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradePrice());
            } else {
                // tradeListMap.get(coinVo.getCode().toString()).add(coinVo.getPrice());
                tradeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradePrice());
            }
            // // 테스트용
            // if (coinVo.getCode().toString().equals("KRW-BTT")) {
            // System.out.println(tradeListMap.get(coinVo.getCode().toString())
            // .get(tradeListMap.get(coinVo.getCode().toString()).size() - 1));
            // }
        } else {
            tradeListMap.put(coinVo.getCode().toString(), new ArrayList<>());
            tradeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradePrice());
        }
    }

    private void setTradingVolumeList(CoinVo coinVo) {
        if (tradeVolumeListMap.containsKey(coinVo.getCode().toString())) {
            if (tradeVolumeListMap.get(coinVo.getCode().toString()).size() > 5000) {
                tradeVolumeListMap.get(coinVo.getCode().toString()).remove(0);
                // tradeVolumeListMap.get(coinVo.getCode().toString()).add(coinVo.getPrice());
                tradeVolumeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradeVolume());
            } else {
                // tradeVolumeListMap.get(coinVo.getCode().toString()).add(coinVo.getPrice());
                tradeVolumeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradeVolume());
            }
        } else {
            tradeVolumeListMap.put(coinVo.getCode().toString(), new ArrayList<>());
            tradeVolumeListMap.get(coinVo.getCode().toString()).add(coinVo.getTradeVolume());
        }
    }

    public Map<String, List<BigDecimal>> getTradeListMap() {
        return tradeListMap;
    }

    public Map<String, List<BigDecimal>> getTradeVolumeListMap() {
        return tradeVolumeListMap;
    }

    private CoinVo makeCoinVo(JSONObject jsonObject) {
        CoinVo coinVo = new CoinVo();
        coinVo.setCode(jsonObject.get("code"));
        coinVo.setSequentialId(jsonObject.get("sequential_id").toString());
        coinVo.setTradePrice(jsonObject.get("trade_price").toString());
        coinVo.setTradeVolume(jsonObject.get("trade_volume").toString());
        coinVo.setPrice(coinVo.getTradePrice(), coinVo.getTradeVolume());
        coinVo.setAskBid(jsonObject.get("ask_bid"));
        coinVo.setTradeTime(jsonObject.get("trade_time"));
        coinVo.setStreamType(jsonObject.get("stream_type"));

        return coinVo;
    }
    // public Map<String, List<Map<String, String>>> showRaceCoin() {
    // findRaceCoin();
    // Map<String, List<Map<String, String>>> coinRank = new HashMap<String,
    // List<Map<String, String>>>();
    // coinRank.put("upCoin", upCoinRank);
    // coinRank.put("downCoin", downCoinRank);
    // return coinRank;
    // }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getAllMarketTrade();
    }
}
