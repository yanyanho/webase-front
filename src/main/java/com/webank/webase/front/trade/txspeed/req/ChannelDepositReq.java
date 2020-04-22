package com.webank.webase.front.trade.txspeed.req;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ChannelDepositReq {

   private  BigInteger channelIdentifier;
   private  String participant;
   private  String partner;
   private  BigInteger totalDeposit;
}
