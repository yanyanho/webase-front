package com.webank.webase.front.trade.asset;

import com.webank.webase.front.base.ConstantCode;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;

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
                    BAC001 bac001 = getBAC001(groupId, userAddress, contractAddress);
                    BigInteger minUnit = bac001.minUnit().send();
                    BigInteger unit = BigInteger.valueOf((long) Math.pow(10, minUnit.doubleValue()));
                    balance = bac001.balance(userAddress).send().divide(unit);
                } else {
                    BAC002 bac002 = getBAC002(groupId, userAddress, contractAddress);
                    balance = bac002.balance(userAddress).send();
                }
            } catch (Exception e) {
                log.error("assetBalance assetId:{} Exception", contractName, e);
                throw new FrontException(ConstantCode.SYSTEM_ERROR);
            }
        }
            return  balance;

    }


        private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = keyStoreService.getCredentials(userAddress,false );
            return BAC001.load(assetAddress, web3j, credentials, contractGasProvider);
        }
        private BAC002 getBAC002(int groupId, String userAddress, String assetAddress) {
            Web3j web3j = web3jMap.get(groupId);
            Credentials credentials = keyStoreService.getCredentials(userAddress,false );
            return BAC002.load(assetAddress, web3j, credentials, contractGasProvider);
        }
}
