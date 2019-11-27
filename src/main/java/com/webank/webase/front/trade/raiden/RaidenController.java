package com.webank.webase.front.trade.raiden;

import com.webank.webase.front.trade.raiden.req.*;
import com.webank.webase.front.trade.raiden.translog.TransferLog;
import com.webank.webase.front.trade.raiden.translog.TransferLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;


@RestController
@RequestMapping("/raiden")
@Api(value = "ExchangeController", tags = "Account Infomation Query")
public class RaidenController {

    @Autowired
    RaidenService raidenService;
    @Autowired
    TransferLogService transferLogService;


    @PostMapping("/create-bac-network")
    @ApiOperation(value = "open channel", httpMethod = "POST")
    public String createBACNetwork(
                                  @RequestParam(defaultValue = "1") int groupId,
                                  @RequestParam String userAddress,
                                  @RequestParam String assetAddress) throws Exception {

        return raidenService.createBACNetwork(groupId, userAddress,assetAddress);
    }

    @PostMapping("/sign")
    @ApiOperation(value = "open channel", httpMethod = "POST")
    public String signMessage(
            @RequestParam String message,
            @RequestParam String userAddress) throws Exception {

        return raidenService.signMessage( message, userAddress);
    }

//    @PostMapping("/sign-balance-proof")
//    @ApiOperation(value = "open channel", httpMethod = "POST")
//    public Map<String,String> signBalanceProof(
//            @RequestParam String message,
//            @RequestParam String userAddress) throws Exception {
//
//        return raidenService.signBalanceProof( message, userAddress);
//    }

    @PostMapping("/channel-open")
    @ApiOperation(value = "open channel", httpMethod = "POST")
    public BigInteger openChannel(@RequestBody ChannelOpenReq channelOpenReq,
                                  @RequestParam(defaultValue = "1") int groupId,
                                  @RequestParam String userAddress,
                                  @RequestParam String bacNetworkAddress) throws Exception {

        return raidenService.openChannel(channelOpenReq, groupId, userAddress,bacNetworkAddress);
    }


    @RequestMapping("/deposit")
    @ApiOperation(value = "deposit", httpMethod = "POST")
    public Boolean deposit(@RequestBody ChannelDepositReq depositReq,
                           @RequestParam(defaultValue = "1") int groupId,
                           @RequestParam String userAddress,
                           @RequestParam String bacNetworkAddress) throws Exception {

        return raidenService.deposit(depositReq,groupId,userAddress,bacNetworkAddress);
    }

    @GetMapping("/participant-info")
    @ApiOperation(value = "participant-info", httpMethod = "GET")
    public Map<String, Object> getChannelParticipantInfo(
                           @RequestParam String partnerAdress,
                           @RequestParam BigInteger channelIdentifier,
                           @RequestParam(defaultValue = "1") int groupId,
                           @RequestParam String userAddress,
                           @RequestParam String bacNetworkAddress) throws Exception {

        return raidenService.getChannelParticipantInfo(partnerAdress,channelIdentifier, groupId,userAddress,bacNetworkAddress);
    }


    @RequestMapping("/withdraw")
    @ApiOperation(value = "withdraw", httpMethod = "POST")
    public Boolean withdraw(@RequestBody ChannelWithdrawReq channelWithdrawReq,
                            @RequestParam(defaultValue = "1") int groupId,
                            @RequestParam String userAddress,
                            @RequestParam String bacNetworkAddress) throws Exception {

        return raidenService.withdraw(channelWithdrawReq,groupId,userAddress,bacNetworkAddress);
    }
// transfer off-chain!!!!!!!!!
    @PostMapping("/transfer")
    @ApiOperation(value = "transfer", httpMethod = "POST")
    public TransferLog sign (@RequestBody TransferLog tranferLog,
                          @RequestParam(defaultValue = "1") int groupId,
                          @RequestParam String userAddress,
                          @RequestParam String bacNetworkAddress) throws Exception {
        return raidenService.transfer(tranferLog,userAddress,bacNetworkAddress);
    }

    @PostMapping("/transfer/cancel")
    @ApiOperation(value = "transfer", httpMethod = "POST")
    public Boolean confirm (@RequestBody tranferLogIdReq tranferLogIdReq) throws Exception {
        return raidenService.confirm(tranferLogIdReq.getTranferLogId());
    }

    @PostMapping("/channel-close")
    @ApiOperation(value = "channel-close", httpMethod = "POST")
    public Boolean closeChannel (@RequestBody ChannelCloseReq channelCloseReq,
                                  @RequestParam(defaultValue = "1") int groupId,
                                  @RequestParam String userAddress,
                                  @RequestParam String bacNetworkAddress) throws Exception {
        return raidenService.closeChannel(channelCloseReq,groupId,userAddress,bacNetworkAddress);
    }

    @PostMapping("/update-balacne-proof")
    @ApiOperation(value = "update balacne proof", httpMethod = "POST")
    public Boolean updateNonClosingBalanceProof (@RequestBody UpdateBalanceProofReq updateBalanceProofReq,
                                                  @RequestParam(defaultValue = "1") int groupId,
                                                  @RequestParam String userAddress,
                                                  @RequestParam String bacNetworkAddress) throws Exception {
        return raidenService.updateNonClosingBalanceProof(updateBalanceProofReq,groupId,userAddress,bacNetworkAddress);
    }

    @PostMapping("/settle")
    @ApiOperation(value = "channel settle", httpMethod = "POST")
    public Boolean settleChannel (@RequestBody ChannelSettleReq channelSettleReq,
                                    @RequestParam(defaultValue = "1") int groupId,
                                    @RequestParam String userAddress,
                                    @RequestParam String bacNetworkAddress) throws Exception {
        return raidenService.settle(channelSettleReq,groupId,userAddress,bacNetworkAddress);
    }

    @GetMapping("/transfer-log")
    @ApiOperation(value = "transfer", httpMethod = "GET")
    public Page<TransferLog> transferLog (@RequestParam BigInteger channelIdentifier,
                                          @RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10")   int pageSize,
                                          @RequestParam String bacNetworkAddress) throws Exception {
        return transferLogService.getTransferLog(pageNumber,pageSize,bacNetworkAddress, channelIdentifier);
    }

    @GetMapping("/channel-state")
    @ApiOperation(value = "channel-state", httpMethod = "GET")
    public Map<String,Object> getChannelInfo (   @RequestParam String participant1,
                                                  @RequestParam String participant2,
                                                  @RequestParam BigInteger channelIdentifier,
                                                  @RequestParam(defaultValue = "1") int groupId,
                                                  @RequestParam String bacNetworkAddress) throws Exception {
        return raidenService.getChannelInfo(participant1,participant2,channelIdentifier,bacNetworkAddress,groupId );
    }




}