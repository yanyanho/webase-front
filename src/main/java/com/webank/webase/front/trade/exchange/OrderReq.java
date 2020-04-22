package com.webank.webase.front.trade.exchange;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class OrderReq {

  private String assetGet;
  private BigDecimal amountGet;
  private String assetGive;
  private BigDecimal amountGive;
  private BigInteger expires;
  private BigInteger assetGetMinUnit;
  private BigInteger assetGiveMinUnit;

}
