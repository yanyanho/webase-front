package com.webank.webase.front.trade.asset;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;


@Api(value = "/asset", tags = "asset interface")
@Slf4j
@RestController
@RequestMapping(value = "/asset")
public class AssetController {


    @Autowired
    AssetService assetService;

    @ApiOperation(value = "assetBalance", notes = "Get asset's accounts")
    @GetMapping("/balance")
    public BigInteger assetBalance(@RequestParam(defaultValue = "BAC001") String contractName,
                                   @RequestParam String contractAddress,
                                   @RequestParam String userAddress,
                                   @RequestParam int groupId)  {
        return assetService.assetBalance(contractName,contractAddress, userAddress,groupId);
    }
}
