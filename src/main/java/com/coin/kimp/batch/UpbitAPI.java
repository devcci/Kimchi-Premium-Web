package com.coin.kimp.batch;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

public class UpbitAPI {

    private final WebClient webClient;
    private static final String API_MIME_TYPE = "application/json";
    private static final String API_BASE_URL = "https://api.upbit.com";
    private static final String API_SUB_URI = "/v1/market/all?isDetails=false";
    private static final String USER_AGENT = "Spring 5 WebClient";
    JSONArray jsonObj;

    public UpbitAPI() {
        this.webClient = WebClient.builder().baseUrl(API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, API_MIME_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT).build();
    }

    // 업비트 마켓 심볼명 중 KRW만 조회하는 API
    public List<String> getMarketNameList() {

        String responseJson = webClient.get().uri(API_SUB_URI).exchange().block().bodyToMono(String.class).block();

        List<String> marketNameList = new ArrayList<String>();
        try {
            JSONParser jsonParser = new JSONParser();
            jsonObj = (JSONArray) jsonParser.parse(responseJson);
            for (int i = 0; i < jsonObj.size(); i++) {
                JSONObject tempJN = (JSONObject) jsonObj.get(i);
                String tempStr = tempJN.get("market").toString();
                if (tempStr.indexOf("KRW") > -1) {
                    marketNameList.add(tempStr);
                }
            }
        } catch (ParseException e) {
            System.out.println("upbit marketName error");
            e.printStackTrace();
        }

        return marketNameList;
    }
}
