package com.webank.webase.front.trade.txspeed.req;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ChannelOpenReq {
    private String participant1;
    private String participant2;
    private BigInteger settleTimeout;
}
