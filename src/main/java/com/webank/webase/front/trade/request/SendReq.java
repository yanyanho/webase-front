package com.webank.webase.front.trade.request;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SendReq {

    private String from;
    private String to;
    private  BigInteger value;
    private BigInteger minUnit;
    private String data;
    private BigInteger assetId;
}
