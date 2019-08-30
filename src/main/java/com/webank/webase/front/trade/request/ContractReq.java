package com.webank.webase.front.trade.request;

import com.webank.webase.front.trade.trade.InitiatorInfo;
import lombok.Data;

import java.math.BigInteger;

@Data
public class ContractReq {

    private String receiver;
    private String secerte;
    private BigInteger lockTime;
    private String assetContractAddress;
    private BigInteger amount;

    private InitiatorInfo initiatorInfo;
}
