package com.webank.webase.front.trade.txspeed.req;

import lombok.Data;
import java.math.BigInteger;

@Data
public class ChannelCloseReq {

  private BigInteger channelIdentifier;
  private String closingParticipant;
  private BigInteger closingParticipantBalance;
  private String nonClosingParticipant;
  private BigInteger nonClosingParticipantBalance;
  private BigInteger nonce;
  private String nonClosingBalanceHash;
  private String nonClosingSignature;
  //private String closingSignature;

}
