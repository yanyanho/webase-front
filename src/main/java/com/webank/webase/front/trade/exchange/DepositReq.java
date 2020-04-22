package com.webank.webase.front.trade.exchange;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class DepositReq {

    private String assetContractAddress;
    private BigDecimal amount;
    private BigInteger minUnit;
}
