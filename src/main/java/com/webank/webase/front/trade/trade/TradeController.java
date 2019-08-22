package com.webank.webase.front.trade.trade;

import com.webank.webase.front.trade.request.ContractReq;
import com.webank.webase.front.trade.request.RefundReq;
import com.webank.webase.front.trade.request.WithDrawReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.fisco.bcos.web3j.tuples.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/trade")
@Api(value = "AccountInfoController", tags = "Account Infomation Query")
public class TradeController {


    @Autowired
    TradeService tradeService;

    @RequestMapping("/new-contract")
    @ApiOperation(value = "new contract", httpMethod = "POST")
    public String createNewCrossContract(@RequestBody ContractReq contractReq,
                                         @RequestParam(defaultValue = "1") int groupId,
                                          @RequestParam String userAddress,
                                          @RequestParam String htlcContractAddress) throws Exception {

        return tradeService.newContract(contractReq,groupId,userAddress,htlcContractAddress);
    }

    @RequestMapping("/withdraw")
    @ApiOperation(value = "withdraw", httpMethod = "POST")
    public String withdrawFund (@RequestBody WithDrawReq withDrawReq,
                                @RequestParam(defaultValue = "1") int groupId,
                                @RequestParam String userAddress,
                                @RequestParam String htlcContractAddress) throws Exception {

        return tradeService.withdraw(withDrawReq,groupId,userAddress,htlcContractAddress);
    }


    @RequestMapping("/refund")
    @ApiOperation(value = "refund", httpMethod = "POST")
    public String withdrawFund (@RequestBody RefundReq refundReq,
                                @RequestParam(defaultValue = "1") int groupId,
                                 @RequestParam String userAddress,
                                 @RequestParam String htlcContractAddress) throws Exception {

        return tradeService.refund(refundReq,groupId,userAddress,htlcContractAddress);
    }

    @RequestMapping("/contract")
    @ApiOperation(value = "refund", httpMethod = "GET")
    public Map getContractInfo(@RequestParam String contractId,
                               @RequestParam(defaultValue = "1") int groupId,
                               @RequestParam String userAddress,
                               @RequestParam String htlcContractAddress) throws Exception {

        return tradeService.getContract(contractId,groupId,userAddress,htlcContractAddress);
    }


}
