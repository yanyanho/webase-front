package com.webank.webase.front;

import com.webank.webase.front.trade.polo.Exchange;
import com.webank.webase.front.trade.polo.HashedTimelockBAC001;
import com.webank.webase.front.trade.polo.TokenNetworkRegistry;
import com.webank.webase.front.trade.raiden.SecretRegistry;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoService;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;

@Component
@Slf4j
public class HTLCDeploy  implements ApplicationRunner {

    @Autowired
    Map<Integer, Web3j> web3jMap;
    @Autowired
    HTLCInfoService htlcInfoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Credentials credentials = Credentials.create("3bed914595c159cbce70ec5fb6aff3d6797e0c5ee5a7a9224a21cae8932d84a4");
        Iterator entries = web3jMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key =  (Integer)entry.getKey();
            Web3j value = (Web3j) entry.getValue();
         //   htlcInfoService.deleteAll();
           if(htlcInfoService.findByGroupId(key.intValue()) == null ) {
                String contractAddress = HashedTimelockBAC001.deploy(value, credentials, contractGasProvider).send().getContractAddress();
                String exchangeContractAddress = Exchange.deploy(value, credentials, contractGasProvider,credentials.getAddress()).send().getContractAddress();
               SecretRegistry secretRegistry = SecretRegistry.deploy(value, credentials, contractGasProvider).send();
               String secretAddress = secretRegistry.getContractAddress();
               //TokenNetworkRegistry
               TokenNetworkRegistry tokenNetworkRegistry = TokenNetworkRegistry.deploy(value, credentials, contractGasProvider,secretAddress,new BigInteger("1"),new BigInteger("1"),new BigInteger("100000000"),new BigInteger("10")).send();

                HTLCInfo htlcInfo = new HTLCInfo();
                htlcInfo.setGroupId(key);
                htlcInfo.setContractAddress(contractAddress);
                htlcInfo.setExchangeContractAddress(exchangeContractAddress);
                htlcInfo.setSecretAddress(secretAddress);
                htlcInfo.setTokenNetworkRegistry(tokenNetworkRegistry.getContractAddress());
                htlcInfo.setCreateTime(LocalDateTime.now());
                htlcInfoService.save(htlcInfo);
                log.info("htlc:{} addresee {} save success", key, contractAddress);
                log.info("exchange:{} addresee {} save success", key, exchangeContractAddress);
                log.info("secretAddress:{} addresee {} save success", key, secretAddress);
            }
        }
    }
}
