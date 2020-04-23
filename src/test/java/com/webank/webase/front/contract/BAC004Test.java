package com.webank.webase.front.contract;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.temp.BAC004;
import org.fisco.bcos.temp.BAC004HolderDemo;
import org.fisco.bcos.temp.BACRegistry;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BAC004Test {


    @Test
    public void testBAC004() throws Exception {

      BigInteger gasPrice = new BigInteger("1");
      BigInteger gasLimit = new BigInteger("2100000000");
      ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice,gasLimit);
      //根据私钥导入账户
      Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
      Credentials credentialsBob = Credentials.create("2");
        // 生成随机私钥使用下面方法；
        // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
      String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
      String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

     String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";
        // 获取spring配置文件，生成上下文
      ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      Service service = context.getBean(Service.class);
      service.run();
      ChannelEthereumService channelEthereumService = new ChannelEthereumService();
      channelEthereumService.setChannelService(service);

      Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        List<String> ilist = new ArrayList<>();
        ilist.add(Bob);

        BACRegistry bacRegistry = BACRegistry.deploy(web3j, credentials, contractGasProvider).send();
        System.out.println("******" + bacRegistry.getContractAddress());
      // 部署合约 即发行资产 资产描述：fisco bcos car asset; 资产简称 TTT; 最小转账单位 1 ;发行总量 10000000;
        BAC004 bac004 = BAC004.deploy(web3j, credentials, contractGasProvider, "GDX car asset", "TTT", ilist, bacRegistry.getContractAddress()).send();
        String contractAddress = bac004.getContractAddress();
          //增加 发行者
        bac004.addIssuer(Bob).send();

       // 增发资产
      bac004.issue(Alice, new BigInteger("30000"),"increase 10000 asset  ".getBytes()).send();
      bac004.issue(Owner, new BigInteger("100000"),"increase 10000 asset  ".getBytes()).send();

      // 销毁资产, owner销毁自己的资产
        bac004.destroy(new BigInteger("10000"), "destroy 10000 asset".getBytes()).send();
       //转账 以及转账备注 Owner -> Alice
       bac004.send(Alice,new BigInteger("10000"),"dinner money".getBytes()).send();
      //test  send to contract
       TransactionReceipt t = bac004.send(bacRegistry.getContractAddress(),new BigInteger("10000"),"dinner money".getBytes()).send();

        assertEquals(t.getStatus(),"0x16");
        BAC004HolderDemo bac004HolderDemo = BAC004HolderDemo.deploy(web3j, credentials, contractGasProvider,bacRegistry.getContractAddress()).send();
        bac004.send(bac004HolderDemo.getContractAddress(),new BigInteger("10000"),"send contract".getBytes()).send();
        assertEquals( bac004.balance(bac004HolderDemo.getContractAddress()).send().toString(),"10000");

        // Owner批准Bob可以从自己账户转走10000TTT
//        bac004.approve(Bob, new BigInteger("10000")).send();
        //Bob开始转走Owner 10000TTT ，需要先根据credentialsBob 重新load合约；
        BAC004 bac004Bob = bac004.load(contractAddress,web3j,credentialsBob,contractGasProvider);
       bac004Bob.operatorSend(Owner,Alice,new BigInteger("10000"),"dddd".getBytes(), "AAAA".getBytes()).send();

//        System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(t.getOutput()));
        //查询余额
        assertEquals( bac004.balance(Alice).send().toString(),"50000");
        assertEquals( bac004.balance(Owner).send().toString(),"60000");

    }

}
