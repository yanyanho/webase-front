package com.miya.token.util;

import com.miya.token.channel.test.TestBase;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.temp.BAC001;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

import static junit.framework.TestCase.assertEquals;

public class BAC001Test extends TestBase{


    @Test
    public void testBAC001() throws Exception {

        BigInteger gasPrice = new BigInteger("1");
        BigInteger gasLimit = new BigInteger("2100000000");
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice,gasLimit);
        //根据私钥导入账户
        Credentials credentials = Credentials.create("b2ba7fb897031a337a48369e62700cb11212ccb2c969deedc7a31a5bf0dd9d47");
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

        // 部署合约 即发行资产 资产描述：fisco bcos car asset; 资产简称 TTT; 最小转账单位 1 ;发行总量 10000000;
        BAC001 bac001 = BAC001.deploy(web3j, credentials, contractGasProvider, "GDX car asset", "TTT",BigInteger.valueOf(1), BigInteger.valueOf(1000000)).send();
        String contractAddress = bac001.getContractAddress();
        System.out.println("*****: "+contractAddress);
        //增加 发行者
//        bac001.addIssuer(Alice).send();
//
//        // 增发资产
//        bac001.issue(Alice, new BigInteger("10000"),"increase 10000 asset  ").send();
//        // 销毁资产, owner销毁自己的资产
//        bac001.destroy(new BigInteger("10000"), "destroy 10000 asset").send();
//        //转账 以及转账备注 Owner -> Alice
//        bac001.send(Alice,new BigInteger("10000"),"dinner money").send();
//        //Owner批准Bob可以从自己账户转走1000TTT
//        bac001.approve(Bob, new BigInteger("10000")).send();
//        //Bob开始转走Owner 1000TTT ，需要先根据credentialsBob 重新load合约；
//        BAC001 bac001Bob = BAC001.load(contractAddress,web3j,credentialsBob,contractGasProvider);
//        bac001Bob.safeSendFrom(Owner,Alice,new BigInteger("10000"),"dddd").send();
//        //查询余额
//        assertEquals( bac001.balance(Alice).send().toString(),"30000");
//        assertEquals( bac001.balance(Owner).send().toString(),"9970000");

    }

}