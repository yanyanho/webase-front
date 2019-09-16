package com.webank.webase.front.trade.exchange;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class TradeReq {

  private BigDecimal amount;
  private BigInteger assetGiveMinUnit;
  private String orderHash;
}
