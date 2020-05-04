package com.webank.webase.front;

import com.webank.webase.front.account.User;
import com.webank.webase.front.account.UserRepository;
import com.webank.webase.front.base.AesUtils;
import com.webank.webase.front.keystore.KeyStoreInfo;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.asset.AssetService;
import com.webank.webase.front.trade.polo.AssetNetworkRegistry;
import com.webank.webase.front.trade.polo.Exchange;
import com.webank.webase.front.trade.polo.HashedTimelockBAC001;
import com.webank.webase.front.trade.polo.SecretRegistry;
import com.webank.webase.front.trade.request.IssueReq;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoService;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;

@Component
@Slf4j
public class HTLCDeploy  implements ApplicationRunner {

    @Autowired
    Map<Integer, Web3j> web3jMap;
    @Autowired
    HTLCInfoService htlcInfoService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AssetService assetService;
    @Autowired
    KeyStoreService keyStoreService;
    @Autowired
    private AesUtils aesUtils;

    public  Credentials credentials = Credentials.create("3bed914595c159cbce70ec5fb6aff3d6797e0c5ee5a7a9224a21cae8932d84a4");


    @Override
    public void run(ApplicationArguments args) throws Exception {

               Iterator entries = web3jMap.entrySet().iterator();
  //    htlcInfoService.deleteAll();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            Integer key =  (Integer)entry.getKey();
            Web3j value = (Web3j) entry.getValue();

           if(htlcInfoService.findByGroupId(key.intValue()) == null ) {
                String contractAddress = HashedTimelockBAC001.deploy(value, credentials, contractGasProvider).send().getContractAddress();
                String exchangeContractAddress = Exchange.deploy(value, credentials, contractGasProvider,credentials.getAddress()).send().getContractAddress();
               SecretRegistry secretRegistry = SecretRegistry.deploy(value, credentials, contractGasProvider).send();
               String secretAddress = secretRegistry.getContractAddress();
               //TokenNetworkRegistry public static RemoteCall<TokenNetworkRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _secret_registry_address, BigInteger _chain_id, BigInteger _settlement_timeout_min, BigInteger _settlement_timeout_max, BigInteger _max_token_networks) {
               AssetNetworkRegistry tokenNetworkRegistry = AssetNetworkRegistry.deploy(value, credentials, contractGasProvider,secretAddress,new BigInteger("1"),new BigInteger("60000"),new BigInteger("864000000"),new BigInteger("10000")).send();
               log.info("************&&&&&&&&&&&&&"  + tokenNetworkRegistry.getContractAddress());
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
        if(userRepository.findByUsername("admin")== null){
            User user = new User();
            user.setId(1L);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUsername("admin");
            userRepository.save(user);

        }

        if(assetService.findDefaultAsset().isEmpty()) {


            if(keyStoreService.getLocalKeyStores("admin").isEmpty()) {
                keyStoreService.keyPair2KeyStoreInfo(credentials.getEcKeyPair(), true, 0, "admin");
               log.info("insert user successfully");
            }

            IssueReq issueReq = new IssueReq();
            issueReq.setDescription("default BAC001 token");
            issueReq.setMinUnit(new BigInteger("0"));
            issueReq.setShortName("AAA-DEMO");
            issueReq.setTotalAmount(new BigInteger("100000000"));
            assetService.issueAsset(issueReq,"BAC001",credentials.getAddress(), 1);

            IssueReq issueReqBBB = new IssueReq();
            issueReqBBB.setDescription("default BAC001 token");
            issueReqBBB.setMinUnit(new BigInteger("0"));
            issueReqBBB.setShortName("BBB-DEMO");
            issueReqBBB.setTotalAmount(new BigInteger("100000000"));
            assetService.issueAsset(issueReqBBB,"BAC001",credentials.getAddress(), 1);

        }

    }
}
