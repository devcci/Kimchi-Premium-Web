<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>김프체크</title>
  <link rel="stylesheet" href="/css/style.css" />
  <link rel="stylesheet" href="/css/bootstrap.min.css" />
  <link rel="shortcut icon" href="#" />
</head>

<body>
  <div class="container">
    <div class="row">
      <div class="col-sm">
        <!-- 도미차트 -->
        <div>
          <div class="tradingview-widget-container">
            <div id="tradingview_a9577"></div>
            <div class="tradingview-widget-copyright">BTC 도미네이션</div>
          </div>
        </div>
      </div>
      <!-- 도미차트 -->
      <div class="col-sm">
        <!-- 김프차트 -->
        <div>
          <div class="tradingview-widget-container">
            <div id="tradingview_155c3"></div>
            <div class="tradingview-widget-copyright">김치프리미엄</div>
          </div>
        </div>
      </div>
      <!-- 김프차트 -->
    </div>
  </div>
  <div id="tickerBoard">

  </div>


  <button type="button" class="btn-close" aria-label="Close"></button>

  <!-- 스크립트 시작 -->
  <script type="text/javascript" src="/js/bootstrap.min.js"></script>
  <script src="/js/jquery-3.6.0.min.js"></script>
  <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
  <script type="text/javascript">
    // //코인 현재가 조회
    setInterval(() => {
      getUpbitCoinName();
    }, 5000);
   
    var arr_krw_markets = "";
    var arr_korean_name = [];

    async function getUpbitCoinName() {

      const options = {
        method: 'GET',
        dataType: "json"
      };

      fetch('https://api.upbit.com/v1/market/all?isDetails=false', options)
        //.then(resa => console.log(resa.json()))
        .then(data => {
          data.json().then(data => {
            for (var i = 0; i < data.length; i++) {
              if (data[i].market.indexOf("KRW") > -1) {
                arr_krw_markets += data[i].market + (",");
                arr_korean_name.push(data[i].korean_name);
              }
            }
            arr_krw_markets = arr_krw_markets.substring(0, arr_krw_markets.length - 1); // 마지막 콤마 제거
            getUpbitCoinTicker();
          })
        })
        .catch(err => console.error(err));
    }

    function getUpbitCoinTicker() {
      const options = {
        method: 'GET',
        dataType: "json"
      };
      fetch('https://api.upbit.com/v1/ticker?markets=' + arr_krw_markets, options)
        .then(tickers => {
          tickers.json().then(tickers => {
            for (let i = 0; i < tickers.length; i++) {
              document.getElementById('tickerBoard').innerHTML +=
                '<ul class="list-group list-group-horizontal">' +
                '<li class="list-group-item flex-fill">' + arr_korean_name[i] + '</li>' +
                '<li class="list-group-item flex-fill">' + JSON.stringify(tickers[i].trade_price) + '</li>' +
                '</ul>';
            }
            //console.log(tickers);




            // <li class="list-group-item">A third item</li>

          })
        }).catch(err => console.error(err));
    }

    
    // new Promise((resolve, reject) => {
    //   getUpbitCoinName(resolve)

    // }).then(() => {
    //   getUpbitCoinTicker();

    // });




    // //  코인 현재가 조회


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
  <!-- 스크립트 끝 -->
</body>

</html>