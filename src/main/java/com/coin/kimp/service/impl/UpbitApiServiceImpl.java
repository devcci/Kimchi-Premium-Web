package com.coin.kimp.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.coin.kimp.batch.UpbitWebSocket;
import com.coin.kimp.service.UpbitApiService;

import org.springframework.stereotype.Service;

@Service("upbitApiService")
public class UpbitApiServiceImpl implements UpbitApiService {

    private List<Map<String, String>> upCoinRankList;
    private List<Map<String, String>> downCoinRankList;
    private List<Map<String, String>> tradeVolumeList;
    UpbitWebSocket webSocket = new UpbitWebSocket();

    // 급등 코인 조회
    public Map<String, List<Map<String, String>>> showRaceCoin() {
        findRaceCoin(webSocket);
        // matchTradeVolue(webSocket);
        Map<String, List<Map<String, String>>> coinRank = new HashMap<String, List<Map<String, String>>>();
        
        coinRank.put("upCoin", upCoinRankList);
        coinRank.put("downCoin", downCoinRankList);
        // coinRank.put("tradingVolume", tradeVolumeList);
        return coinRank;
    }

    // 코인 거래량
    // private void matchTradeVolue(UpbitWebSocket webSocket) {
    //     Map<String, List<BigDecimal>> tradeVolumeListMap = webSocket.getTradeVolumeListMap();
    //     tradeVolumeList = new ArrayList<Map<String, String>>();
    //     for (int i = 0; i < 5; i++) {
    //         Map<String, String> tempMap = new HashMap<String, String>();
    //         tempMap.put("symbol", ResultMapKeySet.get(i));
    //         tempMap.put("volume", resultMap.get(ResultMapKeySet.get(i)).toPlainString());
    //         tradeVolumeList.add(tempMap);
    //     }
    // }

    // 급등/급락 코인 검색
    public void findRaceCoin(UpbitWebSocket webSocket) {
        Map<String, List<BigDecimal>> tradeListMap = webSocket.getTradeListMap();
        Map<String, List<BigDecimal>> tradeVolumeListMap = webSocket.getTradeVolumeListMap();
        Iterator<String> itr = tradeListMap.keySet().iterator();
        List<String> ResultMapKeySet = null;
        Map<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
        if (!itr.hasNext()) {
            System.out.println("마켓데이터 점검 필요");
            return;
        } else {
            while (itr.hasNext()) {
                String key = itr.next();
                BigDecimal firstVal = tradeListMap.get(key).get(0);
                if (firstVal.compareTo(BigDecimal.ZERO) == 0) {
                    // System.out.print(firstVal);
                    // System.out.println("코인명 :" + key + " 가격: 측정X");
                    continue;
                }
                BigDecimal lastVal = tradeListMap.get(key).get(tradeListMap.get(key).size() - 1);
                BigDecimal result = lastVal.subtract(firstVal);
                BigDecimal resultPer = result.divide(firstVal, 6, RoundingMode.HALF_EVEN)
                        .multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_EVEN);
                // System.out.println("코인명: " + key + " 처음 가격: " + firstVal + " 나중 가격: " +
                // lastVal + " 가격격차: " + result
                // + " 퍼센트%: " + resultPer + "%");
                resultMap.put(key, resultPer);
                // BigDecimal result = new BigDecimal(0);
                // BigDecimal sumVal = new BigDecimal(0);
                // String key = itr.next();
                // for (int i = 0; i < tradeListMap.get(key).size(); i++) {
                // sumVal = sumVal.add(tradeListMap.get(key).get(i));
                // }
                // if (sumVal.compareTo(BigDecimal.ZERO) == 0) {
                // System.out.print(sumVal);
                // System.out.println("코인명 :" + key + " 가격: 측정X");
                // continue;
                // }
                // BigDecimal temp = sumVal.divide(new BigDecimal(tradeListMap.get(key).size()),
                // 2,
                // RoundingMode.HALF_EVEN);

                // BigDecimal fistPrice = tradeListMap.get(key).get(0);
                // result = (temp.subtract(fistPrice)).divide(temp, 2, RoundingMode.HALF_EVEN)
                // .multiply(new BigDecimal(100));
                // resultMap.put(key, result);

                // System.out.println("코인명 :" + key + " 가격: " + result);
            }
        }
        ResultMapKeySet = new ArrayList<>(resultMap.keySet());
        // 내림차순 급등코인
        setUpCoinRank(ResultMapKeySet, resultMap);
        // 오름차순 급락코인
        setDownCoinRank(ResultMapKeySet, resultMap);
    }

    // 내림차순 급등코인
    private void setUpCoinRank(List<String> ResultMapKeySet, Map<String, BigDecimal> resultMap) {
        Collections.sort(ResultMapKeySet, (value1, value2) -> (resultMap.get(value2).compareTo(resultMap.get(value1))));
        upCoinRankList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("symbol", ResultMapKeySet.get(i));
            tempMap.put("value", resultMap.get(ResultMapKeySet.get(i)).toPlainString());
            // tempMap.put("volume",)
            upCoinRankList.add(tempMap);
        }
    }

    // 오름차순 급락코인
    private void setDownCoinRank(List<String> ResultMapKeySet, Map<String, BigDecimal> resultMap) {
        Collections.sort(ResultMapKeySet, (value1, value2) -> (resultMap.get(value1).compareTo(resultMap.get(value2))));
        downCoinRankList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 5; i++) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.put("symbol", ResultMapKeySet.get(i));
            tempMap.put("value", resultMap.get(ResultMapKeySet.get(i)).toPlainString());
            downCoinRankList.add(tempMap);
        }
    }
}
