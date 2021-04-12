<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>김프체크</title>
    <link rel="stylesheet" href="/css/style.css" />
  </head>

  <body>
    <div class="table" style="display: block">
      <!-- 도미차트 -->
      <div style="width: 50%">
        <div class="tradingview-widget-container">
          <div id="tradingview_a9577"></div>
          <div class="tradingview-widget-copyright">BTC 도미네이션</div>
        </div>
      </div>
      <!-- 도미차트 -->

      <!-- 김프차트 -->
      <div style="width: 50%">
        <div class="tradingview-widget-container">
          <div id="tradingview_155c3"></div>
          <div class="tradingview-widget-copyright">김치프리미엄</div>
        </div>
      </div>
      <!-- 김프차트 -->
    </div>

    <script
      type="text/javascript"
      src="https://s3.tradingview.com/tv.js"
    ></script>
    <script type="text/javascript">
      var btcDomiChart = new TradingView.widget({
        autosize: true,
        symbol: "CRYPTOCAP:BTC.D",
        interval: "D",
        timezone: "Asia/Seoul",
        theme: "dark",
        style: "1",
        locale: "kr",
        toolbar_bg: "#f1f3f6",
        enable_publishing: false,
        hide_top_toolbar: true,
        save_image: false,
        container_id: "tradingview_a9577",
      });

      var kimchiChart = new TradingView.widget({
        autosize: true,
        symbol: "(BTCKRW/(BTCUSD*USDKRW)*100)-100",
        interval: "D",
        timezone: "Asia/Seoul",
        theme: "dark",
        style: "1",
        locale: "kr",
        toolbar_bg: "#f1f3f6",
        enable_publishing: false,
        hide_top_toolbar: true,
        save_image: false,
        container_id: "tradingview_155c3",
      });
    </script>
  </body>
</html>
