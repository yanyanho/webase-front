package com.webank.webase.front.trade.request;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class WithDrawReq {

    private String contractId;
    private String secerte;
    private BigDecimal value;
    private String  partnerAssetAddress;
    private BigInteger  partnerAssetMinunit;

}
