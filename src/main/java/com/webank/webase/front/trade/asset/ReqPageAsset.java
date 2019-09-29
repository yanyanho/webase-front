package com.webank.webase.front.trade.asset;

import lombok.Data;

@Data
public class ReqPageAsset {
    private String assetId;
    private String contractName;
    private String assetAddress;
    private String owner;
    private Integer assetStatus;
    private Integer assetGroup;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
}
