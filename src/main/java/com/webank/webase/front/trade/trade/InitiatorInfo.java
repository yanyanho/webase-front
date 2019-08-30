package com.webank.webase.front.trade.trade;

import lombok.Data;

import java.math.BigInteger;

@Data
public class InitiatorInfo {
    private String initiatorContractId;
    private int initiatorGroupId;
    private String initiatorAssetContractAddress;
    private String initiatorHtlcContractAddress;
    private BigInteger initiatorValue;
}
