package com.webank.webase.front.trade.raiden.network;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int groupId;
    private String assetAddress;
    private String bacNetWorkAddress;
    private String tokenNetworkRegistry;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
