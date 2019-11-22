package com.webank.webase.front.trade.raiden.req;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UpdateBalanceProofReq {


  private BigInteger channelIdentifier;
  private String closingParticipant;
  private String nonClosingParticipant;
  private   BigInteger closingParticipantBalance;;
  private BigInteger closingParticipantNonce;
  private String balanceHashClosing;
  private String closingSignature;
  private String nonClosingnature;
}
