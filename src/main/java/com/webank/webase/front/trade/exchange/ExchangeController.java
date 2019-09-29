package com.webank.webase.front.trade.exchange;

import com.webank.webase.front.trade.exchange.Order.ExchangeOrder;
import com.webank.webase.front.trade.exchange.Order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;


@RestController
@RequestMapping("/exchange")
@Api(value = "ExchangeController", tags = "Account Infomation Query")
public class ExchangeController {


    @Autowired
    ExchangeService exchangeService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/deposit")
    @ApiOperation(value = "deposit", httpMethod = "POST")
    public Boolean deposit(@RequestBody DepositReq depositReq,
                            @RequestParam(defaultValue = "1") int groupId,
                            @RequestParam String userAddress,
                            @RequestParam String exchangeContractAddress) throws Exception {

        return exchangeService.deposit(depositReq,groupId,userAddress,exchangeContractAddress);
    }

    @RequestMapping("/withdraw")
    @ApiOperation(value = "withdraw", httpMethod = "POST")
    public Boolean withdraw(@RequestBody DepositReq withdrawReq,
                             @RequestParam(defaultValue = "1") int groupId,
                             @RequestParam String userAddress,
                             @RequestParam String exchangeContractAddress) throws Exception {

        return exchangeService.withdraw(withdrawReq,groupId,userAddress,exchangeContractAddress);
    }

    @ApiOperation(value = "balance", notes = "Get asset's accounts")
    @GetMapping("/balance")
    public BigDecimal balance(@RequestParam(defaultValue = "BAC001") String contractName,
                               @RequestParam String exchangeContractAddress,
                               @RequestParam String assetAddress,
                               @RequestParam BigInteger minUnit,
                               @RequestParam String userAddress,
                               @RequestParam(defaultValue = "1") int groupId)  {
        return exchangeService.balance(contractName,exchangeContractAddress, assetAddress, minUnit, userAddress,groupId);
    }


    @RequestMapping("/order")
    @ApiOperation(value = "make order", httpMethod = "POST")
    public String order (@RequestBody OrderReq orderReq,
                          @RequestParam(defaultValue = "1") int groupId,
                          @RequestParam String userAddress,
                          @RequestParam String exchangeContractAddress) throws Exception {

        return exchangeService.order(orderReq,groupId,userAddress,exchangeContractAddress);
    }

    @RequestMapping("/trade")
    @ApiOperation(value = "trade order", httpMethod = "POST")
    public Boolean trade (@RequestBody TradeReq tradeReq,
                           @RequestParam(defaultValue = "1") int groupId,
                           @RequestParam String userAddress,
                           @RequestParam String exchangeContractAddress) throws Exception {
        return exchangeService.trade(tradeReq.getOrderHash(), tradeReq.getAmount(), tradeReq.getAssetGiveMinUnit(),groupId,userAddress,exchangeContractAddress);
    }

    @RequestMapping("/cancel")
    @ApiOperation(value = "cancel order", httpMethod = "POST")
    public Boolean cancle (@RequestBody TradeReq tradeReq,
                           @RequestParam(defaultValue = "1") int groupId,
                           @RequestParam String userAddress,
                           @RequestParam String exchangeContractAddress) throws Exception {
        return exchangeService.cancel(tradeReq.getOrderHash(), tradeReq.getAmount(), tradeReq.getAssetGiveMinUnit(),groupId,userAddress,exchangeContractAddress);
    }

    @RequestMapping("/address")
    @ApiOperation(value = "address", httpMethod = "GET")
    public Map getHTLCAddress()   {
        return exchangeService.getExchangeAddress();
    }


    @RequestMapping("/order/available")
    @ApiOperation(value = "address", httpMethod = "GET")
    public Page<ExchangeOrder> getAvailableOrder(
                                                  @RequestParam(defaultValue = "0") int status,
                                                  @RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "10")   int pageSize)   {
        return orderService.getAvailableOrder(pageNumber, pageSize,status);
    }

}
