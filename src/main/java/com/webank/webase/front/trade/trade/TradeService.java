package com.webank.webase.front.trade.trade;


import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.request.ContractReq;
import com.webank.webase.front.trade.request.RefundReq;
import com.webank.webase.front.trade.request.WithDrawReq;
import com.webank.webase.front.trade.asset.BAC001;
import com.webank.webase.front.trade.asset.HashedTimelockBAC001;
import com.webank.webase.front.util.DecodeOutputUtils;
import com.webank.webase.front.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.Tuple;
import org.fisco.bcos.web3j.tuples.generated.Tuple9;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
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
    private  Map<Integer, String> htlcMap;

    public String newContract(ContractReq contractReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        BAC001 bac001 =  getBAC001(groupId,userAddress, contractReq.getAssetContractAddress());
        TransactionReceipt transactionReceipt001 =bac001.approve(htlcContractAddress,new BigInteger("100000")).send();

        dealWithReceipt(transactionReceipt001);

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] hash ;
        if(contractReq.getFlag()==1) {
             hash = Hash.sha256(Tools.stringToByte32Array(contractReq.getSecerte()));
        } else {
             hash = Util.hexStringToBytes(contractReq.getSecerte());
        }

        TransactionReceipt transactionReceipt = hashedTimelockBAC001.newContract(contractReq.getReceiver(),hash, contractReq.getLockTime(), contractReq.getAssetContractAddress(), contractReq.getAmount()).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();
    }


    public String withdraw(WithDrawReq withDrawReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {
        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);

        byte[] contractId = Util.hexStringToBytes(withDrawReq.getContractId().substring(2));
        TransactionReceipt transactionReceipt = hashedTimelockBAC001.withdraw( contractId,Tools.stringToByte32Array(withDrawReq.getSecerte())).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();


    }

    public String refund(RefundReq refundReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);

        byte[] contractId = Util.hexStringToBytes(refundReq.getContractId().substring(2));
        TransactionReceipt transactionReceipt = hashedTimelockBAC001.refund( contractId).send();

        dealWithReceipt(transactionReceipt);
        return transactionReceipt.getOutput();
    }

    private void dealWithReceipt(TransactionReceipt transactionReceipt) {
        log.info("*********"+ transactionReceipt.getOutput());
        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            throw new FrontException(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
        if (!"0x0".equals(transactionReceipt.getStatus())) {
            throw new FrontException(Thread.currentThread().getStackTrace()[2].getMethodName() + " 交易失败,状态：" + transactionReceipt.getStatus());
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
        return htlcMap;
    }
}
