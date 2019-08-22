package com.webank.webase.front.trade.request;

import lombok.Data;

import java.math.BigInteger;
import java.security.Timestamp;

@Data
public class ContractReq {

    private String receiver;
    private String secerte;
    private BigInteger lockTime;
    private String assetContractAddress;
    private BigInteger amount;

}
