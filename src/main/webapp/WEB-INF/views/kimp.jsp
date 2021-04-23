<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
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


    <!-- 코인 Symbol 목록 -->
    <div id="symbolBtnList" class="button-container">
    </div>


    <button><a href="https://upbit.com/" target="_blank">업비트 이동</a></button>
    <button><a href="https://www.binance.com/ko/register?ref=RZRUT876" target="_blank">바이낸스 이동</a></button>

    <div id="tickerBoard">
      <table class="table">
        <thead>
          <tr>
            <th scope="col">코인</th>
            <th scope="col">업비트</th>
            <th scope="col">바이낸스(원)</th>
            <th scope="col">바이낸스(달러)</th>
            <th scope="col">김프</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
  </div>

  <!-- 스크립트 시작 -->
  <script type="text/javascript" src="/js/bootstrap.min.js"></script>
  <script src="/js/jquery-3.6.0.min.js"></script>
  <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
  <script type="text/javascript" src="/js/common.js"></script>
  <script type="text/javascript">
    var arr_krw_markets = []; // 코인 마켓 배열
    var arr_korean_name = new Map(); // 코인 한글명 배열
    var favorCoinList = getCookie("favorCoin").split(","); // 관심 코인 종목
    var upbitTkcer = new Map();
    var binanceTicker = new Map();
    var binanceFavor = [];

    $(document).ready(function () {
      getUpbitCoinName();
      setInterval(() => {
        getUpbitCoinTicker();
        getBinanceTicker();
        showTickerBoard()
      }, 1000);
    });

    // 숫자 콤마 및 소수점 유지
    function krwComma(num) {
      if(isNaN(num)){
        return "";
      }
      const option = {
        maximumFractionDigits: 2,
        minimumFractionDigits: 2
      }
      if (num == null) {
       return "";
      }else if(num > 100){
        return num.toLocaleString('ko-KR');
      }
      return num.toLocaleString('ko-KR', option);
    }
    function usdComma(num) {
      if(isNaN(num)){
        return "";
      }
      num = parseFloat(num);
      const option1 = {
        maximumFractionDigits: 2,
        minimumFractionDigits: 2
      }
      const option2 = {
        maximumFractionDigits: 4,
        minimumFractionDigits: 4
      }
      const option3 = {
        maximumFractionDigits: 7,
        minimumFractionDigits: 7
      }
      if (num == null) {
       return "";
      }else if(num > 1000){
        return num.toLocaleString('en-US', option1);
      }
    else if(num > 1){
        return num.toLocaleString('en-US', option2);
      }
      return num.toLocaleString('en-US', option3);
    }

    // 코인 마켓 조회
    async function getUpbitCoinName() {
      const options = {
        method: 'GET',
        dataType: "json"
      };

      fetch('https://api.upbit.com/v1/market/all?isDetails=false', options)
        .then(data => {
          data.json().then(data => {
            for (var i = 0; i < data.length; i++) {
              if (data[i].market.indexOf("KRW") > -1) {
                arr_krw_markets.push(data[i].market);
                arr_korean_name.set(data[i].market, data[i].korean_name);
              }
            }

            showSymbol();
          })
        })
        .catch(err => console.error(err));
    }

    // 심볼 버튼 출력
    function showSymbol() {
      var symbolHTML = "";
      arr_krw_markets.sort();
      for (let i = 0; i < arr_krw_markets.length; i++) {
        symbolHTML +=
          "<button type='button' name='symbolBtn' class='btn btn-secondary' onclick='favorToggle(this)' value='" +
          arr_krw_markets[i] + "'><div class=favorText>" + arr_krw_markets[i].substring(4) +
          "</div></button>";
      }
      document.getElementById('symbolBtnList').innerHTML = symbolHTML;
      favorOn();
    }

    // 티커보드 출력
    function showTickerBoard() {
      var tickerHTML;

      for (let i = 0; i < upbitTkcer.size; i++) {
        tickerHTML += '    <tr> ' +
          '      <th scope="row"><div style="float:left;"><img src="https://static.upbit.com/logos/' + favorCoinList[i]
          .substring(4) +
          '.png" alt="https://static.upbit.com/logos/' + favorCoinList[i].substring(4) +
          '.png" class=slogo></div><div>' + favorCoinList[i].substring(4) + '</div><div>' +
          arr_korean_name.get(favorCoinList[i]) + '</div></th> ' +
          '      <td>' + krwComma(upbitTkcer.get(favorCoinList[i])) + '</td> ' +
          '      <td>' + krwComma(binanceTicker.get(favorCoinList[i].substring(4) + "USDT")*1.0012*1115.13) + '</td> ' +
          '      <td>' + usdComma(binanceTicker.get(favorCoinList[i].substring(4) + "USDT")) + '</td> ' +
          '      <td>' + calKimp(upbitTkcer.get(favorCoinList[i]), binanceTicker.get(favorCoinList[i].substring(4) + "USDT"));+ '</td> ' +
          '    </tr> ';
      }
      $('#tickerBoard > table > tbody').empty();
      $('#tickerBoard > table > tbody').html(tickerHTML);
    }

    function calKimp(krw, usd){
      krw = parseFloat(krw);
      usd = parseFloat(usd);
      if(isNaN(usd)){
        return "";
      }
      return (krw /(usd*1.0012*1115.13)*100-100).toFixed(2);
    }
    // 관심 코인 초기로딩 시 색 표시
    function favorOn(params) {
      // var smBtns = $("button[name='symbolBtn']"); 제이쿼리 자제
      var smBtns = document.getElementsByName('symbolBtn');
      for (let i = 0; i < smBtns.length; i++) {
        if (favorCoinList.indexOf(smBtns[i].value) != -1) {
          smBtns[i].className = "btn btn-primary";
        }
      };
    }

    // 심볼 관심종목 추가
    function favorToggle(btn) {
      if (containsCookie("favorCoin", btn.value)) {
        deleteCookie("favorCoin", btn.value);
        btn.className = "btn btn-secondary";
        binanceTicker.clear();
        upbitTkcer.clear();
      } else {
        appendCookie("favorCoin", btn.value, 365);
        btn.className = "btn btn-primary";
      }
      favorCoinList = getCookie("favorCoin").split(",");

      getUpbitCoinTicker();
      getBinanceTicker();
    }

    // 업비트 현재가 조회
    function getUpbitCoinTicker() {
      var favorLen = 0;
      if (favorCoinList[0] != "") {
        favorLen = favorCoinList.length;
      }
      favorCoinList.sort();
      var tickerHTML;
      const options = {
        method: 'GET',
        dataType: "json"
      };
      fetch('https://api.upbit.com/v1/ticker?markets=' + favorCoinList, options)
        .then(tickers => {
          tickers.json().then(tickers => {
            for (let i = 0; i < tickers.length; i++) {
              upbitTkcer.set(tickers[i].market, tickers[i].trade_price);
            }

            // for (let i = 0; i < tickers.length; i++) {
            //   tickerHTML += '    <tr> ' +
            //     '      <th scope="row"><div style="float:left;"><img src="https://static.upbit.com/logos/' +
            //     tickers[i].market
            //     .substring(4) + '.png" alt="https://static.upbit.com/logos/' + tickers[i].market.substring(4) +
            //     '.png" class=slogo></div><div>' + tickers[i].market.substring(4) + '</div><div>' +
            //     arr_korean_name[tickers[i]
            //       .market] + '</div></th> ' +
            //     '      <td>' + krwComma(tickers[i].trade_price) + '</td> ' +
            //     '      <td>' + "추가예정" + '</td> ' +
            //     '      <td>' + "추가예정" + '</td> ' +
            //     '    </tr> ';
            // }
            // $('#tickerBoard > table > tbody').empty();
            // $('#tickerBoard > table > tbody').html(tickerHTML);
          })
        }).catch(err => console.error(err));
    }

    // 바이낸스 현재가 조회
    function getBinanceTicker() {
      for (let i = 0; i < favorCoinList.length; i++) {
        binanceFavor[i] = favorCoinList[i].substring(4) + "USDT";
      }
      for (let i = 0; i < binanceFavor.length; i++) {
        const options = {
          method: 'GET',
          dataType: "json"
        };
        fetch('https://api.binance.com/api/v3/ticker/price?symbol=' + binanceFavor[i], options)
          .then(tickers => {
            tickers.json().then(tickers => {
              binanceTicker.set(tickers.symbol, tickers.price);
            })
          }).catch(err => console.log(err));
      }
    }



    //비트 도미넌스 차트
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

    //김프 차트
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