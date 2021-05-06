package com.coin.kimp.batch;

// import java.util.ArrayList;
// import java.util.List;

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;
// import org.springframework.http.HttpHeaders;
// import org.springframework.web.reactive.function.client.WebClient;

public class CandleAPI {
    // private final WebClient webClient;
    // private static final String API_MIME_TYPE = "application/json";
    // private static final String API_BASE_URL = "https://api.upbit.com";
    // private static final String API_SUB_URI = "/v1/candles";
    // private static final String USER_AGENT = "Spring 5 WebClient";
    // JSONArray jsonObj;

    // public CandleAPI() {
    //     this.webClient = WebClient.builder().baseUrl(API_BASE_URL)
    //             .defaultHeader(HttpHeaders.CONTENT_TYPE, API_MIME_TYPE)
    //             .defaultHeader(HttpHeaders.USER_AGENT, USER_AGENT).build();
    // }

    // public void run(){
    //     UpbitAPI upbitAPI = new UpbitAPI();
    //     List<JSONObject> list = new ArrayList();
    //     List<String> marketNameLst = upbitAPI.getMarketNameList();
    //     String uriArg = "";
    //     for (int i = 0; i < marketNameLst.size(); i++) {
    //             try {
    //                 Thread.sleep(100);
    //             } catch (InterruptedException e1) {
    //                 e1.printStackTrace();
    //             }
    //         String targetCoin = marketNameLst.get(i);
    //         uriArg = "/minutes/1?market=" + targetCoin + "&count=1";

    //         String responseJson = webClient.get().uri(API_SUB_URI + uriArg).exchange().block().bodyToMono(String.class)
    //                 .block();

           
    //         try {
    //             JSONParser jsonParser = new JSONParser();
    //             jsonObj = (JSONArray) jsonParser.parse(responseJson);
    //             for (int j = 0; j < jsonObj.size(); j++) {
    //                 JSONObject tempJN = (JSONObject) jsonObj.get(j);
    //                     list.add(tempJN);
    //             }

    //         } catch (ParseException e) {
    //             System.out.println("업비트 오류");
    //             System.out.println(marketNameLst.get(i));
    //             //e.printStackTrace();
    //         }

    //     }
    // }
    

    // public List<JSONObject> getUpbitCandle() throws InterruptedException {

    //     UpbitAPI upbitAPI = new UpbitAPI();
    //     List<JSONObject> list = new ArrayList();
    //     List<String> marketNameLst = upbitAPI.getMarketNameList();
    //     String uriArg = "";
    //     for (int i = 0; i < marketNameLst.size(); i++) {
    //         String targetCoin = marketNameLst.get(i);
    //         uriArg = "/minutes/1?market=" + targetCoin + "&count=1";

    //         String responseJson = webClient.get().uri(API_SUB_URI + uriArg).exchange().block().bodyToMono(String.class)
    //                 .block();

           
    //         try {
    //             JSONParser jsonParser = new JSONParser();
    //             jsonObj = (JSONArray) jsonParser.parse(responseJson);
    //             for (int j = 0; j < jsonObj.size(); j++) {
    //                 JSONObject tempJN = (JSONObject) jsonObj.get(j);
    //                     list.add(tempJN);
    //             }

    //         } catch (ParseException e) {
    //             System.out.println("업비트 오류");
    //             System.out.println(marketNameLst.get(i));
    //             //e.printStackTrace();
    //         }

    //     }
    //     return list;
    // }

    // public void getRaceHorse() {

    // }
}
