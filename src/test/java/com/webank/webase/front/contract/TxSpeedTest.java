package com.webank.webase.front.contract;

import com.webank.webase.front.Application;
import com.webank.webase.front.base.CryptoUtil;
import com.webank.webase.front.trade.polo.BAC001;
import com.webank.webase.front.trade.polo.SecretRegistry;
import com.webank.webase.front.trade.polo.TokenNetwork;
import com.webank.webase.front.trade.polo.TokenNetworkRegistry;
import com.webank.webase.front.util.DecodeOutputUtils;
import org.fisco.bcos.channel.client.Service;

import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fisco.bcos.web3j.crypto.Keys.getAddress;
import static org.fisco.bcos.web3j.crypto.Sign.signedMessageToKey;
import static org.fisco.bcos.web3j.utils.Numeric.hexStringToByteArray;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TxSpeedTest {


    @Autowired
    private Map<Integer, Web3j> web3jMap;

    BigInteger gasPrice = new BigInteger("1");
    BigInteger gasLimit = new BigInteger("2100000000");
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    //根据私钥导入账户
    Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    Credentials credentialsBob = Credentials.create("2");
    // 生成随机私钥使用下面方法；
    // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
    String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
    String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

    String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";

    @Test
    public void testRaiden() throws Exception {

        Web3j web3j = web3jMap.get(new Integer("1"));

        System.out.println("########" + web3j.getBlockNumber().send());
        //fist  secretRegistty
      SecretRegistry secretRegistry = SecretRegistry.deploy(web3j, credentials, contractGasProvider).send();
      String secretAddress = secretRegistry.getContractAddress();
        // asset
        BAC001 abcToken = BAC001.deploy(web3j, credentials, contractGasProvider,"TXSPEDDE","TXSPEDDE",new BigInteger("0"),new BigInteger("1000000")).send();
        String tokenAddress = abcToken.getContractAddress();
        System.out.println(abcToken.totalAmount().send());
        abcToken.send(Bob,new BigInteger("20000"), "".getBytes()).send();
        System.out.println("Bob balance: " + abcToken.balance(Bob).send() );
        System.out.println("Owner balance: " + abcToken.balance(Owner).send() );
        System.out.println("token contract address: " + tokenAddress);
        //TokenNetworkRegistry
                                                                                                                         //address _token_address, address _secret_registry, uint256 _chain_id, uint256 _settlement_timeout_min, uint256 _settlement_timeout_max, address _deprecation_executor, uint256 _channel_participant_deposit_limit, uint256 _token_network_deposit_limit
        TokenNetworkRegistry tokenNetworkRegistry = TokenNetworkRegistry.deploy(web3j, credentials, contractGasProvider,secretAddress,new BigInteger("1"),new BigInteger("60000"),new BigInteger("864000000"),new BigInteger("100000")).send();


                                                                     //address _token_address, address _secret_registry, uint256 _chain_id, uint256 _settlement_timeout_min, uint256 _settlement_timeout_max, address _deprecation_executor, uint256 _channel_participant_deposit_limit, uint256 _token_network_deposit_limit
        TransactionReceipt t = tokenNetworkRegistry.createERC20TokenNetwork(tokenAddress,new BigInteger("1000000000000000000000000"),new BigInteger("1000000000000000000000000")).send();

        List<TokenNetworkRegistry.TokenNetworkCreatedEventResponse>  responses =  tokenNetworkRegistry.getTokenNetworkCreatedEvents(t);
        String token_network_address = responses.get(0).token_network_address;
        System.out.println("token_network_address: " + token_network_address);
        //load token network
        TokenNetwork tokenNetwork = TokenNetwork.load(token_network_address, web3j, credentials, contractGasProvider);

        // open channel   settle timeout!!!
       TransactionReceipt t1 = tokenNetwork.openChannel(Owner,Bob,new BigInteger("60000")).send();
       dealWithReceipt(t1);
        TokenNetwork.ChannelOpenedEventResponse response1 = tokenNetwork.getChannelOpenedEvents(t1).get(0);
        System.out.println("channel_identifier: " +response1.channel_identifier +
                " participant1: "+ response1.participant1 +
               " participant2: "+ response1.participant2+
                " settle_timeout: "+ response1.settle_timeout);
// approve
        abcToken.approve(token_network_address,new BigInteger("20000")).send();
        System.out.println("owner allowance: " + abcToken.allowance(Owner,token_network_address).send());

// owner deposit 500
        TransactionReceipt t2 =  tokenNetwork.setTotalDeposit(new BigInteger("1"),Owner,new BigInteger("200"),Bob).send();
        TransactionReceipt t6 =  tokenNetwork.setTotalDeposit(new BigInteger("1"),Owner,new BigInteger("500"),Bob).send();


        System.out.println("t2:"+ t2.getStatus() +"  Bob balance: " + abcToken.balance(Bob).send() );
        System.out.println("Owner balance: " + abcToken.balance(Owner).send() );
        TokenNetwork tokenNetworkBob =  TokenNetwork.load(token_network_address, web3j, credentialsBob, contractGasProvider);

        BAC001 abcTokenBob = BAC001.load(tokenAddress,web3j, credentialsBob, contractGasProvider);

        abcTokenBob.approve(token_network_address,new BigInteger("2000")).send();
        System.out.println("bob allowance: " + abcTokenBob.allowance(Bob,token_network_address).send());

        // Bob deposit 1000
        TransactionReceipt tt = tokenNetworkBob.setTotalDeposit(new BigInteger("1"),Bob,new BigInteger("1000"),Owner).send();

        TokenNetwork.ChannelNewDepositEventResponse response2 = tokenNetwork.getChannelNewDepositEvents(tt).get(0);
        System.out.println("channel_identifier: " +response2.channel_identifier +
                " participant: "+ response2.participant +
                " total_deposit: "+ response2.total_deposit);
        System.out.println("Owner balance: " + abcToken.balance(Owner).send() );
        System.out.println("Bob balance: " + abcToken.balance(Bob).send() );

        Tuple2<BigInteger, BigInteger> tuple2 =  tokenNetwork.getChannelInfo(new BigInteger("1"), Owner,Bob).send();
        System.out.println("settle blockNumber: " + tuple2.getValue1().toString());
        //    NonExistent, // 0  Opened,      // 1  Closed,      // 2  Settled,     // 3; Note: The channel has at least one pending unlock  Removed      // 4; Note: Channel data is removed, there are no pending unlocks
        System.out.println(tuple2.getValue2().toString());

        Tuple7<BigInteger, BigInteger, Boolean, byte[], BigInteger, byte[], BigInteger> tuple7 =  tokenNetwork.getChannelParticipantInfo(new BigInteger("1"),Bob,Owner ).send();

        System.out.println(tuple7.getValue5());


        System.out.println(web3j.getBlockNumber().send().getBlockNumber());
       BigInteger channel_identifier =  new BigInteger("1");
       BigInteger expiration_block =  new BigInteger("10000");


        //   bytes32 message_hash = keccak256(abi.encodePacked(address(this),chain_id,uint256(MessageTypeId.Withdraw),channel_identifier,participant,total_withdraw,expiration_block));

        BigInteger chain_id = new BigInteger("1");
        BigInteger total_withdraw = new BigInteger("100");
       //sign
        System.out.println("sign: " +  token_network_address);
        byte[] bytes = CryptoUtil.solidityBytes( new Address("0x00eba1b7d30c40b025591d5f7020421e4cb9ebab"),chain_id,new BigInteger("3"),chain_id,new Address(Owner),new BigInteger("100"), new BigInteger("10000"));
        byte[] bytes555  = CryptoUtil.soliditySha3( new Address("0x00eba1b7d30c40b025591d5f7020421e4cb9ebab"),chain_id,new BigInteger("3"),chain_id,new Address(Owner),new BigInteger("100"), new BigInteger("10000"));
        System.out.println("555: " + Numeric.toHexString(bytes));
        Sign.SignatureData sigData = Sign.getSignInterface().signMessage(bytes, credentials.getEcKeyPair());
         byte[] participantSign = signatureDataToBytes(sigData);
        System.out.println("sign1 :" + Numeric.toHexString(participantSign));

        byte[] bytes1 = CryptoUtil.solidityBytes( new Address(token_network_address),chain_id,new BigInteger("3"),chain_id,new Address(Owner),total_withdraw, expiration_block);
        Sign.SignatureData sigData1 = Sign.getSignInterface().signMessage(bytes1, credentialsBob.getEcKeyPair());
        byte[] participantSign1 = signatureDataToBytes(sigData1);


//        Tuple2<String, byte[]> i = tokenNetwork1.recoverAddressFromWithdrawMessageForTest(chain_id,Owner, new BigInteger("100"), expiration_block,sign).send();
//        System.out.println( i.getValue1()); //
//        System.out.println( bytesToHex(i.getValue2()));   //message

     // owner withdraw100
        tokenNetwork.setTotalWithdraw(channel_identifier, Owner, new BigInteger("100"),expiration_block,participantSign,participantSign1 ).send();
        System.out.println("Owner balance: " + abcToken.balance(Owner).send() );
        //  owner 400 , bob 10000    send off-chain  nonce =5
        BigInteger nonce = new BigInteger("5");
        // bob 22
        BigInteger particantBalanceBob = new BigInteger("22");
        BigInteger lockBalance = new BigInteger("0");
         byte[] lockroot =  new byte[32];

        //sign   owner close channel  should get Bob bl sign
        byte[] balance_hash_bob = CryptoUtil.soliditySha3( particantBalanceBob,lockBalance,lockroot);
        byte[] addtional_hash = new byte[32];

        byte[] bytesBalance2 = CryptoUtil.solidityBytes( new Address(token_network_address),chain_id,new BigInteger("1"),chain_id,balance_hash_bob,nonce, addtional_hash);
        Sign.SignatureData sigDataBalance2 = Sign.getSignInterface().signMessage(bytesBalance2, credentialsBob.getEcKeyPair());
        byte[] participantSignBalance2 = signatureDataToBytes(sigDataBalance2);

        byte[] bytesBalance1 = CryptoUtil.solidityBytes( new Address(token_network_address),chain_id,new BigInteger("1"),chain_id,balance_hash_bob,nonce , addtional_hash,participantSignBalance2);
        Sign.SignatureData sigDataBalance1 = Sign.getSignInterface().signMessage(bytesBalance1, credentials.getEcKeyPair());
        byte[] participantSignBalance1 = signatureDataToBytes(sigDataBalance1);


        byte[]  non_closing_signature = participantSignBalance2;
        byte[]  closing_signature = participantSignBalance1 ;

//        String ttt=  tokenNetwork.recoverAddressFromBalanceProofCounterSignature(new BigInteger("1"),chain_id,balance_hash,nonce,addtional_hash,participantSignBalance2,participantSignBalance1).send();
//        System.out.println(ttt);

        TransactionReceipt ttt1=    tokenNetwork.closeChannel(channel_identifier,Bob,Owner,balance_hash_bob,nonce, addtional_hash ,participantSignBalance2,participantSignBalance1).send();
        TokenNetwork.ChannelClosedEventResponse  closeEvent = tokenNetwork.getChannelClosedEvents(ttt1).get(0);
        System.out.println("nonce:" +closeEvent.nonce +" close: "+closeEvent.closing_participant);


        //!!!!!!!!!!!!!!!!!!!!!!
        //sign   owner close channel  should get Bob bl sign
    // Owner
        BigInteger particantBalanceOwner = new BigInteger("32");
        byte[] balance_hash_owner = CryptoUtil.soliditySha3( particantBalanceOwner,lockBalance,lockroot);


        byte[] bytesBalance21 = CryptoUtil.solidityBytes( new Address(token_network_address),chain_id,new BigInteger("1"),chain_id,balance_hash_owner,nonce , addtional_hash);
        Sign.SignatureData sigDataBalance21 = Sign.getSignInterface().signMessage(bytesBalance21, credentials.getEcKeyPair());
        byte[] participantSignBalance21 = signatureDataToBytes(sigDataBalance21);

        byte[] bytesBalance22 = CryptoUtil.solidityBytes( new Address(token_network_address),chain_id, new BigInteger("2"),chain_id, balance_hash_owner, nonce, addtional_hash, participantSignBalance21);
        Sign.SignatureData sigDataBalance22 = Sign.getSignInterface().signMessage(bytesBalance22, credentialsBob.getEcKeyPair());
        byte[] participantSignBalance22 = signatureDataToBytes(sigDataBalance22);

        tokenNetwork.updateNonClosingBalanceProof(channel_identifier,Owner,Bob,balance_hash_owner,nonce,addtional_hash,participantSignBalance21,participantSignBalance22).send();

        Tuple7<BigInteger, BigInteger, Boolean, byte[], BigInteger, byte[], BigInteger> tuple =  tokenNetwork.getChannelParticipantInfo(new BigInteger("1"),Bob,Owner ).send();
        Tuple7<BigInteger, BigInteger, Boolean, byte[], BigInteger, byte[], BigInteger> tuple11 =  tokenNetwork.getChannelParticipantInfo(new BigInteger("1"),Owner,Bob ).send();
// 32  22

        Tuple2<BigInteger, BigInteger> tuple22 =  tokenNetwork.getChannelInfo(new BigInteger("1"), Owner,Bob).send();

        // add block
//        for(int i =0 ; i< 10; i++) {
//            abcTokenBob.approve(token_network_address, new BigInteger("4000")).send();
//            Thread.sleep(50);
//        }
        System.out.println("settle time: " + tuple22.getValue1().toString());
        Thread.sleep(60000);
     // settle block number must less than block number
        tokenNetwork.settleChannel(channel_identifier,Owner,particantBalanceOwner,new BigInteger("0"), lockroot, Bob,particantBalanceBob,new BigInteger("0"), lockroot).send();

        System.out.println(abcToken.balance(Owner).send());
        System.out.println(abcToken.balance(Bob).send());


    }

    private Web3j getWeb3j() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("start --------------------- ");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        return Web3j.build(channelEthereumService, service.getGroupId());
    }


    public  String  signatureDataToString(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  Numeric.toHexString(byte_3,0,byte_3.length,false);
    }
    static public  byte[]  signatureDataToBytes(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  byte_3;
    }


    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {
        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            System.out.println("0x16");
            System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
    }


















    // 存钱
    public Sign.SignatureData testHash(byte[] bytes) throws SignatureException {

       // String message = "\\x19Ethereum Signed Message:\n" + hash1.length() + hash1;

    //    byte[] data = message.getBytes();
     //   System.out.println("0x" + bytesToHex(Hash.sha3(bytes)));
        Credentials c = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

        System.out.println("address " + c.getAddress());
        Sign.SignatureData sigData = Sign.getSignInterface().signMessage(bytes, c.getEcKeyPair());


        System.out.println("Signature v value: " + (int) sigData.getV());
        System.out.println("Signature r value: 0x" + bytesToHex(sigData.getR()));
        System.out.println("Signature s value: 0x" + bytesToHex(sigData.getS()));

       // System.out.println("Signature: 0x" + HashTest.signatureDataToString(sigData));
        System.out.println("address " + "0x" + getAddress(signedMessageToKey(Hash.sha3(bytes), sigData)));
        return sigData;
    }



    private static String bytesToHex(byte[] bytes)
    {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        String finalHex = new String(hexChars);
        return finalHex;
    }


}
