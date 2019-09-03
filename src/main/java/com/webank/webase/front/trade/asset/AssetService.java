package com.webank.webase.front.trade.asset;

import com.webank.webase.front.base.ConstantCode;
import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.request.IssueReq;
import com.webank.webase.front.trade.request.SendReq;
import com.webank.webase.front.util.DecodeOutputUtils;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;
import static com.webank.webase.front.trade.trade.TradeService.dealWithReceipt;

@Slf4j
@Service
public class AssetService {
    @Autowired
    private Map<Integer, Web3j> web3jMap;

    @Autowired
    KeyStoreService keyStoreService;

    public BigInteger assetBalance(String contractName, String contractAddress, String userAddress, int groupId) throws FrontException {
        BigInteger balance = new BigInteger("0");
        if (!userAddress.equals("0x0000000000000000000000000000000000000000")) {
            try {
                if ("BAC001".equals(contractName)) {
                    BAC001 bac001 = getBAC001OnlyQueryChain(groupId, contractAddress);
                    BigInteger minUnit = bac001.minUnit().send();
                    BigInteger unit = BigInteger.valueOf((long) Math.pow(10, minUnit.doubleValue()));
                    balance = bac001.balance(userAddress).send().divide(unit);
                } else {
                    BAC002 bac002 = getBAC002OnlyQueryChain(groupId,  contractAddress);
                    balance = bac002.balance(userAddress).send();
                }
            } catch (Exception e) {
                log.error("assetBalance assetId:{} Exception", contractName, e);
                throw new FrontException(e.getMessage());
            }
        }
            return  balance;

    }


        private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) throws IOException {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = keyStoreService.getCredentials(userAddress,false );
            BAC001 bac001 =  BAC001.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac001.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//            }
            return bac001;
        }

        private BAC001 getBAC001OnlyQueryChain(int groupId , String assetAddress) throws IOException {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = Credentials.create("2");
             BAC001 bac001 = BAC001.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac001.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//            }
            return bac001;
        }


        private BAC002 getBAC002(int groupId, String userAddress, String assetAddress) throws IOException {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = keyStoreService.getCredentials(userAddress,false );
            BAC002 bac002 = BAC002.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac002.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//            }
            return bac002;
        }

        private BAC002 getBAC002OnlyQueryChain(int groupId , String assetAddress) throws IOException {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = Credentials.create("2");
            BAC002 bac002 =  BAC002.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac002.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//
//            }
            return bac002;
    }


    public BACInfo assetInfo(String contractName, String contractAddress, int groupId) throws Exception {
       if("BAC001".equals(contractName)) {
           BAC001 bac001 = getBAC001OnlyQueryChain(groupId,  contractAddress);
           String description = bac001.description().send();
           BigInteger totalAmount = bac001.totalAmount().send();
           BigInteger minUnit = bac001.minUnit().send();
           String shortName = bac001.shortName().send();
           Boolean status = bac001.suspended().send();
           return new BACInfo(description, totalAmount, minUnit, shortName,status,contractAddress,contractName,groupId);
       }
       else {
           BAC002 bac002 = getBAC002OnlyQueryChain(groupId, contractAddress);
           String description = bac002.description().send();
           BigInteger totalAmount = bac002.totalSupply().send();
           String shortName = bac002.shortName().send();
           Boolean status = bac002.suspended().send();
           return new BACInfo(description, totalAmount, new BigInteger("0"), shortName,status,contractAddress,contractName,groupId);
       }
    }

    public Boolean sendFund(SendReq sendReq, String contractName, String contractAddress, int groupId) throws Exception {

        if("BAC001".equals(contractName)) {
            BAC001 bac001 = getBAC001(groupId, sendReq.getFrom(), contractAddress);
             String to = sendReq.getTo();
             String data = sendReq.getData();
            BigInteger minUnit = sendReq.getMinUnit();
             if(sendReq.getMinUnit()== null) {
                  minUnit = bac001.minUnit().send();
             }
            BigInteger realAmount = BigInteger.valueOf((long) Math.pow(10,minUnit.doubleValue())).multiply( sendReq.getValue());
          TransactionReceipt transactionReceipt =  bac001.send(to,realAmount,data).send();
            dealWithReceipt(transactionReceipt);
            return true;
        }
        else {
            BAC002 bac002 = getBAC002(groupId, sendReq.getFrom(), contractAddress);
            TransactionReceipt transactionReceipt =  bac002.sendFrom(sendReq.getFrom(),sendReq.getTo(),sendReq.getAssetId(),sendReq.getData().getBytes()).send();
            dealWithReceipt(transactionReceipt);
        }
            return true;
    }

    public String issueAsset(IssueReq issueReq, String contractName, String userAddress, int groupId) throws Exception {

        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );

        if("BAC001".equals(contractName)) {
            BAC001 bac001 = BAC001.deploy(web3j, credentials, Constants.contractGasProvider, issueReq.getDescription(), issueReq.getShortName(), issueReq.getMinUnit(), issueReq.getTotalAmount()).send();
            return bac001.getContractAddress();
        }
        return null;
    }

}
