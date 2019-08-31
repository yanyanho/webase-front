package com.miya.token.trade.request;


import lombok.Data;

import java.math.BigInteger;

@Data
public class IssueReq {

    private String description;
    private String shortName;
    private BigInteger minUnit;
    private BigInteger totalAmount;
}
