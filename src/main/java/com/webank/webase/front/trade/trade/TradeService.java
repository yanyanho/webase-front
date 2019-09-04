package com.webank.webase.front.trade.trade;


import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.request.ContractReq;
import com.webank.webase.front.trade.request.RefundReq;
import com.webank.webase.front.trade.request.WithDrawReq;
import com.webank.webase.front.trade.asset.BAC001;
import com.webank.webase.front.trade.asset.HashedTimelockBAC001;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoService;
import com.webank.webase.front.util.DecodeOutputUtils;
import com.webank.webase.front.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple9;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fisco.bcos.web3j.crypto.gm.sm3.Util.encodeHexString;

@Slf4j
@Service
public class TradeService {


    @Autowired
    private Map<Integer, Web3j> web3jMap;

    @Autowired
    KeyStoreService keyStoreService;

    @Autowired
    HTLCInfoService htlcInfoService;

    public static Map<Integer, String> imap = new HashMap<>();


    public String newContractForInitiator(ContractReq contractReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        BAC001 bac001 =  getBAC001(groupId,userAddress, contractReq.getAssetContractAddress());
        BigInteger minUnit = bac001.minUnit().send();
        BigInteger value =   BigInteger.valueOf((long) Math.pow(10,minUnit.doubleValue())).multiply(contractReq.getAmount());

        TransactionReceipt transactionReceipt001 =bac001.approve(htlcContractAddress,value).send();
        dealWithReceipt(transactionReceipt001);
        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] hash ;
        hash = Hash.sha256(Tools.stringToByte32Array(contractReq.getSecerte()));

        TransactionReceipt transactionReceipt = hashedTimelockBAC001.newContract(contractReq.getReceiver(),hash, contractReq.getLockTime(), contractReq.getAssetContractAddress(), value).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();
    }


    public String newContractForReceiver(ContractReq contractReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        BAC001 bac001 =  getBAC001(groupId, userAddress, contractReq.getAssetContractAddress());
        BigInteger minUnit = bac001.minUnit().send();
        BigInteger value =   BigInteger.valueOf((long) Math.pow(10,minUnit.doubleValue())).multiply(contractReq.getAmount());

        int initiatorGroupId  =  contractReq.getInitiatorInfo().getInitiatorGroupId();
        BigInteger initiatorValue = contractReq.getInitiatorInfo().getInitiatorValue();
        String initiatorAssetContractAddress = contractReq.getInitiatorInfo().getInitiatorAssetContractAddress();
        BigInteger initiatorMinUnit = contractReq.getInitiatorInfo().getInitiatorAssetMinunit();
        BigInteger initiatorRealValue = getInitiatorValue(initiatorMinUnit, userAddress, initiatorGroupId, initiatorValue, initiatorAssetContractAddress);

        chekoutCounterpartyDeposit(initiatorRealValue,contractReq.getInitiatorInfo().getInitiatorContractId(), initiatorGroupId ,userAddress,contractReq.getInitiatorInfo().getInitiatorHtlcContractAddress());

        TransactionReceipt transactionReceipt001 =bac001.approve(htlcContractAddress,value).send();
        dealWithReceipt(transactionReceipt001);

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] hash ;
        hash = Util.hexStringToBytes(contractReq.getSecerte());

        TransactionReceipt transactionReceipt = hashedTimelockBAC001.newContract(contractReq.getReceiver(),hash, contractReq.getLockTime(), contractReq.getAssetContractAddress(), value).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();


    }

    private BigInteger getInitiatorValue(BigInteger initiatorMinUnit, String userAddress, int initiatorGroupId, BigInteger initiatorValue, String initiatorAssetContractAddress) throws Exception {
        BAC001 initiatorBac001 = getBAC001(initiatorGroupId,userAddress,initiatorAssetContractAddress);
        if(initiatorMinUnit ==null) {
             initiatorMinUnit = initiatorBac001.minUnit().send();
        }
        return BigInteger.valueOf((long) Math.pow(10, initiatorMinUnit.doubleValue())).multiply(initiatorValue);
    }

    public String withdraw(WithDrawReq withDrawReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {
        BigInteger minUint = withDrawReq.getPartnerAssetMinunit();

        if(minUint == null) {
          BAC001 bac001 = getBAC001(withDrawReq.getPartnerGroupId(), userAddress, withDrawReq.getPartnerAssetAddress());
          minUint = bac001.minUnit().send();
       }

       BigInteger realValue  =  BigInteger.valueOf((long) Math.pow(10, minUint.doubleValue())).multiply(withDrawReq.getValue());
      chekoutCounterpartyDeposit(realValue ,withDrawReq.getContractId(), groupId,userAddress,htlcContractAddress);
        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);

        byte[] contractId = Util.hexStringToBytes(withDrawReq.getContractId().substring(2));
        TransactionReceipt transactionReceipt = hashedTimelockBAC001.withdraw( contractId,Tools.stringToByte32Array(withDrawReq.getSecerte())).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();


    }

    private void chekoutCounterpartyDeposit(BigInteger value, String contractId, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] contractIdBytes = Util.hexStringToBytes(contractId.substring(2));
        Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]> output = hashedTimelockBAC001.getContract(contractIdBytes).send();
        BigInteger amount   = output.getValue4();

        if(value .intValue() != amount.intValue()) {
            throw new FrontException("the counterpart's value not match");
        }

    }

    public String refund(RefundReq refundReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);

        byte[] contractId = Util.hexStringToBytes(refundReq.getContractId().substring(2));
        TransactionReceipt transactionReceipt = hashedTimelockBAC001.refund(contractId).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();
    }

    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {
       // log.info("*********"+ transactionReceipt.getOutput());
        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            throw new FrontException(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
        if (!"0x0".equals(transactionReceipt.getStatus())) {
            throw new FrontException(Thread.currentThread().getStackTrace()[2].getMethodName() + " 交易失败,状态：" + transactionReceipt.getStatus() + "交易返回 " + transactionReceipt.getOutput());
        }
    }


    private HashedTimelockBAC001 getHashedTimelockBAC001(int groupId, String userAddress, String htlcContractAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return HashedTimelockBAC001.load(htlcContractAddress, web3j, credentials, Constants.contractGasProvider);
    }

    private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return BAC001.load(assetAddress, web3j, credentials, Constants.contractGasProvider);
    }

    public Map getContract(String contractId, int groupId, String userAddress, String htlcContractAddress) throws Exception {
        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] contractIdBytes = Util.hexStringToBytes(contractId.substring(2));
        Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]> output = hashedTimelockBAC001.getContract(contractIdBytes).send();
        Map<String,Object> imap = new HashMap<>();

       imap.put("sender", output.getValue1());
       imap.put("receiver", output.getValue2());
       imap.put("assetContract",output.getValue3());
       imap.put("amount",  output.getValue4());
       imap.put("hashlock",encodeHexString(output.getValue5()));
       imap.put("timelock",output.getValue6());
       imap.put("withdrawn",output.getValue7());
       imap.put("refunded",output.getValue8());
       imap.put("preimage",encodeHexString(output.getValue9()));

        return imap;

    }

    public Map getHTLCAddress() {

        List<HTLCInfo> htlcInfos = htlcInfoService.findAll();

        for (int i = 0; i < htlcInfos.size(); i++) {
            HTLCInfo htlcInfo = htlcInfos.get(i);
            imap.put(htlcInfo.getGroupId(),htlcInfo.getContractAddress());
        }
        return imap;
    }

}
