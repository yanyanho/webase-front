package com.webank.webase.front.trade.txspeed.translog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
public class TransferLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bacNetworkAddress;
    private BigInteger channelIdentifier;
    private String fromAddress;
    private String toAddress;
    private BigInteger value;
    private BigInteger nonce;
    private String fromBalanceHash;
    private String fromSignature;
    private String participant1;
    private String participant2;
    private BigInteger participant1Balance;
    private BigInteger participant2Balance;
    private Boolean status;

    private String closingBalanceHash;
    private String closingSignature;

    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
