package com.webank.webase.front.web3j;

import com.webank.webase.front.trade.polo.BAC001;
import com.webank.webase.front.util.DecodeOutputUtils;
import com.webank.webase.front.util.Tools;
import org.fisco.bcos.channel.client.Service;

import org.fisco.bcos.temp.HashedTimelockBAC001;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

public class HashTimeLockBAC001Test {

    @Test
    public void testBAC001() throws Exception {

      BigInteger gasPrice = new BigInteger("1");
      BigInteger gasLimit = new BigInteger("2100000000");
      ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice,gasLimit);
      //根据私钥导入账户
      Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
      Credentials credentialsBob = Credentials.create("2");
        // 生成随机私钥使用下面方法；
        // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
      String Bob   = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
      String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";
      String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";
        // 获取spring配置文件，生成上下文
      ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      Service service = context.getBean(Service.class);
      service.run();
      ChannelEthereumService channelEthereumService = new ChannelEthereumService();
      channelEthereumService.setChannelService(service);

      Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());

      //发行bac001
      BAC001 bac001 = BAC001.deploy(web3j, credentialsBob, contractGasProvider, "GDX car asset", "TTT",BigInteger.valueOf(1), BigInteger.valueOf(1000000)).send();
      String contractAddress = bac001.getContractAddress();
        System.out.println("contract address : " +  contractAddress);

      HashedTimelockBAC001 hashTimeLockBAC001 =  HashedTimelockBAC001.deploy(web3j, credentials, contractGasProvider).send();
      String hashtimeLockAddress = hashTimeLockBAC001.getContractAddress();
      // 批准合约可以转走资产
      bac001.approve(hashtimeLockAddress,new BigInteger("100000")).send();

      byte[] hash = Hash.sha256(Tools.stringToByte32Array("hello"));
      TransactionReceipt transactionReceipt = hashTimeLockBAC001.newContract(Bob,hash, BigInteger.valueOf(System.currentTimeMillis()+50000),contractAddress,new BigInteger("100")).send();
      System.out.println(transactionReceipt.getStatus());
      System.out.println(transactionReceipt.getOutput());
      System.out.println("hash time lock contract balance:" + bac001.balance(hashtimeLockAddress).send());
//        List<HashedTimelockBAC001.LogHTLCBAC001NewEventResponse> logHTLCBAC001NewEvents = hashTimeLockBAC001.getLogHTLCBAC001NewEvents(transactionReceipt);
//        byte[] contractId = logHTLCBAC001NewEvents.get(0).contractId;
       String contractId1 = transactionReceipt.getOutput();
       if(contractId1.startsWith("0x08c379a0")) {
           System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
           System.exit(1);
       }
        System.out.println(contractId1);
         byte[] contractFromOutPut = Util.hexStringToBytes(contractId1.substring(2));
     // Tuple9<String, String, String, BigInteger, byte[], BigInteger, Boolean, Boolean, byte[]> contract = hashTimeLockBAC001.getContract(contractId).send();
//      Thread.sleep(3000);
//      hashTimeLockBAC001.refund(contractId).send();
        System.out.println("Bob withdraw before: " + bac001.balance(Bob).send());
        HashedTimelockBAC001 hashedTimelockBAC001Bob = HashedTimelockBAC001.load(hashtimeLockAddress, web3j, credentialsBob, contractGasProvider);
       TransactionReceipt transactionReceipt1 = hashedTimelockBAC001Bob.withdraw(contractFromOutPut,Tools.stringToByte32Array("hello")).send();
//        System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt1.getOutput()));
        System.out.println("Bob withdraw after: " + bac001.balance(Bob).send());
    }

}
