package com.webank.webase.front.trade.asset;

import com.alibaba.fastjson.JSON;
import com.webank.webase.front.base.BasePageResponse;
import com.webank.webase.front.base.ConstantCode;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.trade.request.IssueReq;
import com.webank.webase.front.trade.request.SendReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Api(value = "/asset", tags = "asset interface")
@Slf4j
@RestController
@RequestMapping(value = "/asset")
public class AssetController {


    @Autowired
    AssetService assetService;

    @ApiOperation(value = "assetBalance", notes = "Get asset's accounts")
    @GetMapping("/balance")
    public BigDecimal assetBalance(@RequestParam(defaultValue = "BAC001") String contractName,
                                   @RequestParam String contractAddress,
                                   @RequestParam String userAddress,
                                   @RequestParam(defaultValue = "1") int groupId) {
        return assetService.assetBalance(contractName, contractAddress, userAddress, groupId);
    }

    @ApiOperation(value = "assetInfo", notes = "Get asset's info")
    @GetMapping("/info")
    public BACInfo assetInfo(@RequestParam(defaultValue = "BAC001") String contractName,
                             @RequestParam String contractAddress,
                             @RequestParam(defaultValue = "1") int groupId) throws Exception {
        return assetService.assetInfo(contractName, contractAddress, groupId);
    }

    @PostMapping("/transfer")
    public Boolean assetTransfer(@RequestBody SendReq sendReq,
                                 @RequestParam(defaultValue = "BAC001") String contractName,
                                 @RequestParam String contractAddress,
                                 @RequestParam(defaultValue = "1") int groupId) throws Exception {
        return assetService.sendFund(sendReq, contractName, contractAddress, groupId);
    }

    @PostMapping("/issue")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public String assetIssue(@RequestBody IssueReq issueReq,
                             @RequestParam(defaultValue = "BAC001") String contractName,
                             @RequestParam String userAddress,
                             @RequestParam(defaultValue = "1") int groupId) throws Exception {
        return assetService.issueAsset(issueReq, contractName, userAddress, groupId);
    }

    /**
     * query list of asset.
     */
    @ApiOperation(value = "query list of asset", notes = "query list of asset ")
    @ApiImplicitParam(name = "req", value = "param info", required = true, dataType = "ReqPageAsset")
    @PostMapping(value = "/assetList")
    public BasePageResponse findByPage(@RequestBody ReqPageAsset req) throws FrontException {
        log.info("findByPage start. ReqPageAsset:{}", JSON.toJSONString(req));
        Page<AssetDO> page = assetService.findAssetByPage(req);
        BasePageResponse response = new BasePageResponse(ConstantCode.RET_SUCCEED);
        response.setTotalCount(page.getTotalElements());
        response.setData(page.getContent());
        return response;
    }


    @GetMapping("/default-asset")
    public BasePageResponse findDefaultAssetList() throws FrontException {
        List<AssetDO> assetList = assetService.findDefaultAsset();
        BasePageResponse response = new BasePageResponse(ConstantCode.RET_SUCCEED);
        response.setData(assetList);
        return response;
    }




    @GetMapping("/search")
    public List<AssetDO> findAssetList( @RequestParam String shortName,  @RequestParam(defaultValue = "1") int groupId) throws FrontException {
        List<AssetDO> assetList = assetService.findAssetByShortName(shortName);
//        BasePageResponse response = new BasePageResponse(ConstantCode.RET_SUCCEED);
//        response.setData(assetList);
        return assetList;
    }
}
