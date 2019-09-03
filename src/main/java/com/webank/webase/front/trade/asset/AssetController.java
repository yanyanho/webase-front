package com.webank.webase.front.trade.asset;

import com.webank.webase.front.trade.request.IssueReq;
import com.webank.webase.front.trade.request.SendReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                   @RequestParam(defaultValue = "1") int groupId)  {
            return assetService.assetBalance(contractName,contractAddress, userAddress,groupId);
    }

    @ApiOperation(value = "assetInfo", notes = "Get asset's info")
    @GetMapping("/info")
    public BACInfo assetInfo(@RequestParam(defaultValue = "BAC001") String contractName,
                              @RequestParam String contractAddress,
                              @RequestParam(defaultValue = "1") int groupId) throws Exception {
            return assetService.assetInfo(contractName,contractAddress,groupId);
    }

    @PostMapping("/transfer")
    public Boolean assetTransfer( @RequestBody SendReq sendReq,
                                   @RequestParam(defaultValue = "BAC001") String contractName,
                                   @RequestParam String contractAddress,
                                   @RequestParam(defaultValue = "1") int groupId) throws Exception {
          return   assetService.sendFund(sendReq,contractName,contractAddress,groupId);
    }

    @PostMapping("/issue")
    public String assetIssue ( @RequestBody IssueReq issueReq,
                               @RequestParam(defaultValue = "BAC001") String contractName,
                               @RequestParam String userAddress,
                               @RequestParam(defaultValue = "1") int groupId) throws Exception {
        return   assetService.issueAsset(issueReq,contractName,userAddress,groupId);
    }
}
