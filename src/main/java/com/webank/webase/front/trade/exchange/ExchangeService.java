package com.webank.webase.front.trade.exchange;


import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.CryptoUtil;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.asset.BAC001;
//import com.webank.webase.front.trade.exchange.ExchangeOrder.ExchangeOrder;
import com.webank.webase.front.trade.asset.Exchange;
import com.webank.webase.front.trade.exchange.Order.ExchangeOrder;
import com.webank.webase.front.trade.exchange.Order.OrderService;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoService;
import com.webank.webase.front.util.DecodeOutputUtils;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;
import static com.webank.webase.front.base.CryptoUtil.bytesToHex;
import static org.fisco.bcos.web3j.crypto.gm.sm3.Util.encodeHexString;

@Slf4j
@Service
public class ExchangeService {


    @Autowired
    private Map<Integer, Web3j> web3jMap;

    @Autowired
    KeyStoreService keyStoreService;

    @Autowired
    HTLCInfoService htlcInfoService;

    @Autowired
    OrderService orderService;

    public static Map<Integer, String> imap = new HashMap<>();


    public Boolean deposit(DepositReq depositReq, int groupId, String userAddress, String exchangeContractAddress) throws Exception {

        BAC001 bac001 =  getBAC001(groupId,userAddress, depositReq.getAssetContractAddress());
        BigInteger minUnit = depositReq.getMinUnit();
        BigInteger value =   depositReq.getAmount().multiply(BigDecimal.valueOf( Math.pow(10,minUnit.doubleValue()))).toBigInteger();

        TransactionReceipt transactionReceipt001 = bac001.approve(exchangeContractAddress,value).send();
        dealWithReceipt(transactionReceipt001);
        Exchange exchange = getExchangeBAC001(groupId, userAddress, exchangeContractAddress);
        TransactionReceipt transactionReceipt = exchange.depositAsset(depositReq.getAssetContractAddress(), value).send();

        dealWithReceipt(transactionReceipt);
        return true;
    }
  public Boolean withdraw(DepositReq depositReq, int groupId, String userAddress, String exchangeContractAddress) throws Exception {

        BigInteger minUnit = depositReq.getMinUnit();
        BigInteger value =   depositReq.getAmount().multiply(BigDecimal.valueOf( Math.pow(10,minUnit.doubleValue()))).toBigInteger();

        Exchange exchange = getExchangeBAC001(groupId, userAddress, exchangeContractAddress);
        TransactionReceipt transactionReceipt = exchange.withdrawAsset(depositReq.getAssetContractAddress(), value).send();
        dealWithReceipt(transactionReceipt);
        return true;
    }

    public String order(OrderReq orderReq, int groupId, String userAddress, String exchangeContractAddress) throws Exception {
        Exchange exchange = getExchangeBAC001(groupId, userAddress, exchangeContractAddress);
        BigInteger randomid = BigInteger.valueOf(System.currentTimeMillis());

        BigInteger assetGetMinUnit =  orderReq.getAssetGetMinUnit();
        BigInteger assetGiveMinUnit = orderReq.getAssetGiveMinUnit();
        BigInteger amountGetValue =   orderReq.getAmountGet().multiply(BigDecimal.valueOf( Math.pow(10,assetGetMinUnit.doubleValue()))).toBigInteger();
        BigInteger amountGiveValue =  orderReq.getAmountGive().multiply(BigDecimal.valueOf( Math.pow(10,assetGiveMinUnit.doubleValue()))).toBigInteger();

        TransactionReceipt transactionReceipt = exchange.order(orderReq.getAssetGet(), amountGetValue,orderReq.getAssetGive(),amountGiveValue,orderReq.getExpires(),randomid).send();
        dealWithReceipt(transactionReceipt);
        String orderHash =  transactionReceipt.getOutput();

        log.info("orderHash: = " + orderHash);
        //sign
        byte[] bytes = CryptoUtil.soliditySha256(new Address(exchangeContractAddress),new Address(orderReq.getAssetGet()), amountGetValue,new Address(orderReq.getAssetGive()),amountGiveValue,orderReq.getExpires(),randomid);
        String orderHash1 = Util.encodeHexString(bytes);
        log.info("orderHash1: = " + orderHash1);


        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        Sign.SignatureData sigData = Sign.getSignInterface().signMessage(bytes, credentials.getEcKeyPair());

          BigInteger v =   BigInteger.valueOf((long) sigData.getV());
          log.info("v: = " + v);
          String r   =     bytesToHex(sigData.getR());
          log.info("r: = " + r);
          String s   =     bytesToHex(sigData.getS());
          log.info("s: = " + s);

        ExchangeOrder exchangeOrder = new ExchangeOrder();
        exchangeOrder.setOrderHash(orderHash);
        exchangeOrder.setRandom(randomid);
        exchangeOrder.setAssetGet(orderReq.getAssetGet());
        exchangeOrder.setAmountGet(amountGetValue);
        exchangeOrder.setAmountGetLeft(amountGetValue);
        exchangeOrder.setAssetGive(orderReq.getAssetGive());
        exchangeOrder.setAmountGive(amountGiveValue);
        exchangeOrder.setExpires(orderReq.getExpires());
        exchangeOrder.setAssetGetMinUnit(orderReq.getAssetGetMinUnit());
        exchangeOrder.setAssetGiveMinUnit(orderReq.getAssetGiveMinUnit());
        exchangeOrder.setV(v);
        exchangeOrder.setR(r);
        exchangeOrder.setS(s);
        exchangeOrder.setStatus(0);
        exchangeOrder.setUpdateTime(LocalDateTime.now());
        exchangeOrder.setMaker(userAddress);
        saveOrderToDB(exchangeOrder);
        return orderHash;
    }

    private void saveOrderToDB(ExchangeOrder exchangeOrderHash) {
         orderService.save(exchangeOrderHash);


    }


    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {

        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            throw new FrontException(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
        if (!"0x0".equals(transactionReceipt.getStatus())) {
            throw new FrontException(Thread.currentThread().getStackTrace()[2].getMethodName() + " 交易失败,状态：" + transactionReceipt.getStatus() + "交易返回 " + transactionReceipt.getOutput());
        }
    }


    private Exchange getExchangeBAC001(int groupId, String userAddress, String exchangeContractAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return Exchange.load(exchangeContractAddress, web3j, credentials, Constants.contractGasProvider);
    }

    private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return BAC001.load(assetAddress, web3j, credentials, Constants.contractGasProvider);
    }

    public Map getExchangeAddress() {

        List<HTLCInfo> htlcInfos = htlcInfoService.findAll();

        for (int i = 0; i < htlcInfos.size(); i++) {
            HTLCInfo htlcInfo = htlcInfos.get(i);
            imap.put(htlcInfo.getGroupId(),htlcInfo.getContractAddress());
        }
        return imap;
    }


    public BigDecimal balance( String contractName, String exchangeContractAddress, String assetAddress,  BigInteger minUnit, String userAddress, int groupId) {

            BigDecimal balance = new BigDecimal("0");
            if (!userAddress.equals("0x0000000000000000000000000000000000000000")) {
                try {
                        Exchange exchange = getExchangeOnlyQueryChain(groupId, exchangeContractAddress);

                        BigInteger unit = BigInteger.valueOf((long) Math.pow(10, minUnit.doubleValue()));
                        BigInteger userBalance = exchange.balanceOf(assetAddress, userAddress).send();
                        balance = new BigDecimal(userBalance).divide(new BigDecimal(unit), minUnit.intValue(), RoundingMode.HALF_EVEN);


                } catch (Exception e) {
                    log.error("assetBalance assetId:{} Exception", contractName, e);
                    throw new FrontException(e.getMessage());
                }
            }
            return  balance;

        }


    private Exchange getExchangeOnlyQueryChain(int groupId , String assetAddress) throws IOException {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = Credentials.create("2");
        Exchange exchange = Exchange.load(assetAddress, web3j, credentials, contractGasProvider);
        return exchange;
    }

    public Boolean trade(String orderHash, BigDecimal amount, BigInteger assetGetMinUnit, int groupId, String userAddress, String exchangeContractAddress) throws Exception {

        Exchange exchange = getExchangeBAC001(groupId, userAddress, exchangeContractAddress);
        ExchangeOrder exchangeOrder =  orderService.findById(orderHash);
        log.info("orderhash: " + exchangeOrder.getOrderHash()  + "create time: "  + exchangeOrder.getCreateTime());
        //trade(address assetGet, uint amountGet, address assetGive, uint amountGive, uint expires, uint nonce, address user, uint8 v, bytes32 r, bytes32 s, uint amount) {

        BigInteger takerAmount = amount.multiply(BigDecimal.valueOf( Math.pow(10,assetGetMinUnit.doubleValue()))).toBigInteger();

        TransactionReceipt transactionReceipt = exchange.trade(exchangeOrder.getAssetGet(),exchangeOrder.getAmountGet(), exchangeOrder.getAssetGive(),exchangeOrder.getAmountGive(),exchangeOrder.getExpires(),exchangeOrder.getRandom(),exchangeOrder.getMaker(),
                exchangeOrder.getV(), Util.hexStringToBytes(exchangeOrder.getR()), Util.hexStringToBytes(exchangeOrder.getS()),takerAmount).send();

        dealWithReceipt(transactionReceipt);

        if(takerAmount.intValue() == exchangeOrder.getAmountGetLeft().intValue() ) {
            log.info( "takeramount: "+ takerAmount.intValue());
            log.info( "amoutget: "+ exchangeOrder.getAmountGetLeft().intValue());
            exchangeOrder.setStatus(1);
        }
        else {

            BigInteger oldAmountGet = exchangeOrder.getAmountGetLeft();
            exchangeOrder.setAmountGetLeft(oldAmountGet.subtract(takerAmount));
            log.info(" oldAmountGet:" + oldAmountGet);
            log.info(" amoutget:" + oldAmountGet.subtract(takerAmount));
        }
        orderService.save(exchangeOrder);
        return true;
    }


    public Boolean cancel(String orderHash, BigDecimal amount, BigInteger assetGiveMinUnit, int groupId, String userAddress, String exchangeContractAddress) throws Exception {

        Exchange exchange = getExchangeBAC001(groupId, userAddress, exchangeContractAddress);
        ExchangeOrder exchangeOrder =  orderService.findById(orderHash);

        //trade(address assetGet, uint amountGet, address assetGive, uint amountGive, uint expires, uint nonce, address user, uint8 v, bytes32 r, bytes32 s, uint amount) {

      //  BigInteger takerAmount = amount.multiply(BigDecimal.valueOf( Math.pow(10,assetGiveMinUnit.doubleValue()))).toBigInteger();

        TransactionReceipt transactionReceipt = exchange.cancelOrder(exchangeOrder.getAssetGet(),exchangeOrder.getAmountGet(), exchangeOrder.getAssetGive(),exchangeOrder.getAmountGive(),exchangeOrder.getExpires(),exchangeOrder.getRandom(),
         exchangeOrder.getV(), Util.hexStringToBytes(exchangeOrder.getR()), Util.hexStringToBytes(exchangeOrder.getS())).send();

        dealWithReceipt(transactionReceipt);
        exchangeOrder.setStatus(4);
        orderService.save(exchangeOrder);
        return true;
    }
}
