package com.webank.webase.front.trade.exchange.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity
public class ExchangeOrder {
    @Id
    private String orderHash;
    //0 订单部分处理  1 订单完成 3 超时 4 取消
    //  EXPIRED, CANCELLED, FILLABLE, FULLY_FILLED
    private int    status;
    private BigInteger availableVolumn;
    private BigInteger random;
    private String     assetGet;
    private BigInteger amountGet;
    private String     assetGive;
    private BigInteger amountGive;
    private BigInteger expires;
    private BigInteger assetGetMinUnit;
    private BigInteger assetGiveMinUnit;
    private BigInteger v;
    private String     r;
    private String     s;
    private String maker;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime createTime;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime updateTime;

}
