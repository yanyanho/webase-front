package com.webank.webase.front.trade.raiden.req;

import lombok.Data;
import java.math.BigInteger;

@Data
public class ChannelCloseReq {

  private BigInteger channelIdentifier;
  private String closingParticipant;
  private String nonClosingParticipant;
  private BigInteger nonClosingParticipantBalance;
  private BigInteger nonce;
  private String balanceHashNonClosing;
  private String nonClosingSignature;
  //private String closingSignature;

}
