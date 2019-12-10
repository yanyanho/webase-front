package com.webank.webase.front.trade.txspeed.req;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ChannelSettleReq {


   private  BigInteger channelIdentifier;
   private  String participant1;
   private  BigInteger participant1TransferredAmount;
   private  BigInteger participant1LockedAmount;
    private String participant2;
   private  BigInteger participant2TransferredAmount;
   private  BigInteger participant2LockedAmount;

}
