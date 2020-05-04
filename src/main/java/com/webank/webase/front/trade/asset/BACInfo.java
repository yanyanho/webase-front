package com.webank.webase.front.trade.asset;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@AllArgsConstructor
@Data
public class BACInfo {

 private String description;
 private BigInteger totalAmount;
 private BigInteger minUnit;
 private String shortName;
 private Boolean status;

 private String contractAddress;
 private String contractName;
 private int groupId;

}
