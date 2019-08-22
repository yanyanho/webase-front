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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

@Slf4j
@Service
public class TradeService {


    @Autowired
    private Map<Integer, Web3j> web3jMap;

    @Autowired
    KeyStoreService keyStoreService;


    public String newContract(ContractReq contractReq, int groupId, String userAddress, String htlcContractAddress) throws Exception {

        BAC001 bac001 =  getBAC001(groupId,userAddress, contractReq.getAssetContractAddress());
        TransactionReceipt transactionReceipt001 =bac001.approve(htlcContractAddress,new BigInteger("100000")).send();

        dealWithReceipt(transactionReceipt001);

        HashedTimelockBAC001 hashedTimelockBAC001 = getHashedTimelockBAC001(groupId, userAddress, htlcContractAddress);
        byte[] hash = Hash.sha256(Tools.stringToByte32Array(contractReq.getSecerte()));

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
        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            throw new FrontException(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
        if ("0x0".equals(transactionReceipt.getStatus())) {
            throw new FrontException(Thread.currentThread().getStackTrace()[1].getMethodName() + " 交易失败,状态：" + transactionReceipt.getStatus());
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

}
