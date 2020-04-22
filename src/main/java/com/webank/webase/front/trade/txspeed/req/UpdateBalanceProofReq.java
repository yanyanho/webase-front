package com.webank.webase.front.trade.txspeed.req;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UpdateBalanceProofReq {


  private BigInteger channelIdentifier;
  private String closingParticipant;
  private String nonClosingParticipant;
  private BigInteger closingParticipantBalance;;
  private BigInteger closingParticipantNonce;
  private String closingBalanceHash;
  private String closingSignature;
  private String nonClosingnature;
}
