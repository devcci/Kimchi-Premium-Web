package com.coin.kimp.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CoinVo {
    private Object code; // code 심볼명
    private Object sequentialId; // sequential_id 트레이드 고유값
    private BigDecimal tradePrice; // 체결가 trade_price
    private BigDecimal tradeVolume; // 체결량 trade_volume
    private int price; // 체결금액
    private Object askBid; // ask매도 bid매수 ask_bid
    private Object tradeTime; // 매매시간 trade_time
    private Object streamType; // 매매타입(REALTIME, SNAPSHOT) stream_type

    public void setTradePrice(Object obj) {

        this.tradePrice = new BigDecimal(obj.toString()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setTradeVolume(Object obj) {

        this.tradeVolume = new BigDecimal(obj.toString()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setPrice(BigDecimal tradePrice, BigDecimal tradeVolume) {
        this.price = tradePrice.multiply(tradeVolume).setScale(0, RoundingMode.HALF_EVEN).intValue();
    }
}