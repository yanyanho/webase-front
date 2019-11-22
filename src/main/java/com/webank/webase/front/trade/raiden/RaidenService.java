package com.webank.webase.front.trade.raiden;


import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.CryptoUtil;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.exchange.Order.OrderService;
import com.webank.webase.front.trade.polo.BAC001;
import com.webank.webase.front.trade.polo.Exchange;
import com.webank.webase.front.trade.polo.TokenNetwork;
import com.webank.webase.front.trade.polo.TokenNetworkRegistry;
import com.webank.webase.front.trade.raiden.req.*;
import com.webank.webase.front.trade.raiden.translog.TransferLog;
import com.webank.webase.front.trade.raiden.translog.TransferLogRepository;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoService;
import com.webank.webase.front.util.DecodeOutputUtils;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webank.webase.front.base.Constants.contractGasProvider;
import static org.fisco.bcos.web3j.crypto.gm.sm3.Util.encodeHexString;

@Slf4j
@Service
public class RaidenService {

   public static BigInteger chain_id = new BigInteger("1");
    @Autowired
    private Map<Integer, Web3j> web3jMap;

    @Autowired
    KeyStoreService keyStoreService;

    @Autowired
    HTLCInfoService htlcInfoService;

    @Autowired
    OrderService orderService;

    @Autowired
    TransferLogRepository transferLogRepository;
    public static Map<Integer, String> imap = new HashMap<>();

   public static Credentials credentialsObserver = Credentials.create("2");




    public BigInteger openChannel(ChannelOpenReq channelOpenReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);

        TransactionReceipt transactionReceipt = tokenNetwork.openChannel(channelOpenReq.getParticipant1(), channelOpenReq.getParticipant2(),channelOpenReq.getSettleTimeout()).send();

        dealWithReceipt(transactionReceipt);
        TokenNetwork.ChannelOpenedEventResponse response1 = tokenNetwork.getChannelOpenedEvents(transactionReceipt).get(0);
        return response1.channel_identifier;

    }


    public Boolean deposit(ChannelDepositReq channelDepositReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);
       String bacAddress =   tokenNetwork.token().send();
        BAC001  bac001 = getBAC001(groupId,userAddress,bacAddress);
        TransactionReceipt transactionReceipt001 = bac001.approve(tokenNetworkAddress,channelDepositReq.getTotalDeposit()).send();
        dealWithReceipt(transactionReceipt001);

        TransactionReceipt transactionReceipt = tokenNetwork.setTotalDeposit(channelDepositReq.getChannelIdentifier(), channelDepositReq.getParticipant(),channelDepositReq.getTotalDeposit(),channelDepositReq.getPartner()).send();

        dealWithReceipt(transactionReceipt);
        return true;

    }


    public Map<String, Object> getChannelParticipantInfo(String partnerAdress,BigInteger channelIdentifier, int groupId, String userAddress, String bacNetworkAddress) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, bacNetworkAddress);
        Tuple7<BigInteger, BigInteger, Boolean, byte[], BigInteger, byte[], BigInteger> tuple7 =
                tokenNetwork.getChannelParticipantInfo(channelIdentifier,userAddress,partnerAdress ).send();

        Map<String,Object> imap = new HashMap<>();

        imap.put("deposit", tuple7.getValue1());
        imap.put("withdraw", tuple7.getValue2());
        imap.put("isTheCloser",tuple7.getValue3());
        imap.put("balanchHash",  encodeHexString(tuple7.getValue4()));
        imap.put("nonce",tuple7.getValue5());
        imap.put("lockroot",encodeHexString(tuple7.getValue6()));
        imap.put("lockedAmount",tuple7.getValue7());
        return  imap;
    }



  public Boolean withdraw(ChannelWithdrawReq channelWithdrawReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {

      TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);


      BigInteger total_withdraw = channelWithdrawReq.getTotalWithdraw();
      String participant = channelWithdrawReq.getParticipant();
      String partner = channelWithdrawReq.getPartner();
      BigInteger channelIdentifier = channelWithdrawReq.getChannelIdentifier();
      BigInteger expirationBlock = channelWithdrawReq.getExpirationBlock();

      //sign
      byte[] bytes = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id, MessageTypeId.Withdraw,channelIdentifier,new Address(participant),total_withdraw, expirationBlock);

      Credentials credentials = keyStoreService.getCredentials(participant,false );
      Sign.SignatureData sigData = Sign.getSignInterface().signMessage(bytes, credentials.getEcKeyPair());
      byte[] participantSign = signatureDataToBytes(sigData);
      byte[] bytes1 = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id,MessageTypeId.Withdraw,chain_id,new Address(participant),total_withdraw, expirationBlock);

      Credentials credentialsPartner = keyStoreService.getCredentials(partner,false );
      Sign.SignatureData sigData1 = Sign.getSignInterface().signMessage(bytes1, credentialsPartner.getEcKeyPair());
      byte[] partnerSign = signatureDataToBytes(sigData1);

        TransactionReceipt transactionReceipt = tokenNetwork.setTotalWithdraw(channelIdentifier,participant,total_withdraw,  expirationBlock,participantSign, partnerSign ).send();
        dealWithReceipt(transactionReceipt);
        return true;
    }


    public String signMessage(String mesaage, String userAddress) {
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        Sign.SignatureData sigDataBalance2 = Sign.getSignInterface().signMessage(Util.hexStringToBytes(mesaage), credentials.getEcKeyPair());
        byte[] signMessage = signatureDataToBytes(sigDataBalance2);
        return  Util.encodeHexString(signMessage);
    }



// 必须拥有对方对自己余额的签名
    //100  ---- 200
    // 90------210                      nonce+1,各自签名自己的余额。balance_hash
    public Boolean closeChannel(ChannelCloseReq channelCloseReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);
        BigInteger nonce = channelCloseReq.getNonce();
        BigInteger channelIdentifier =   channelCloseReq.getChannelIdentifier();

        String closingParticipant = channelCloseReq.getClosingParticipant();
        String nonClosingParticipant = channelCloseReq.getNonClosingParticipant();

        //  sign   owner close channel  should get Bob bl sign
        BigInteger particantNonclosingBalance = channelCloseReq.getNonClosingParticipantBalance();
        // lock is not use
//        BigInteger lockBalance = new BigInteger("0");
//        byte[] lockroot =  new byte[32];
//        byte[] balance_hash_non_closing = CryptoUtil.soliditySha3( particantNonclosingBalance,lockBalance,lockroot);
        byte[]   balance_hash_non_closing =  Util.hexStringToBytes(channelCloseReq.getBalanceHashNonClosing());
        byte[] addtional_hash = new byte[32];

 //       byte[] bytesBalance2 = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id, MessageTypeId.BalanceProof,chain_id, balance_hash_non_closing,nonce, addtional_hash);
 //       Credentials nonClosingParticipantCredential = keyStoreService.getCredentials(nonClosingParticipant,false );
  //      Sign.SignatureData sigDataBalance2 = Sign.getSignInterface().signMessage(bytesBalance2, nonClosingParticipantCredential.getEcKeyPair());
 //       byte[] participantSignBalance2 = signatureDataToBytes(sigDataBalance2);
       byte[] participantSignBalance2 = Util.hexStringToBytes(channelCloseReq.getNonClosingSignature());

        byte[] bytesBalance1 = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id, MessageTypeId.BalanceProof.getValue(), channelIdentifier, balance_hash_non_closing,nonce , addtional_hash, participantSignBalance2);
        Credentials closingParticipantCredential = keyStoreService.getCredentials(closingParticipant,false );
        Sign.SignatureData sigDataBalance1 = Sign.getSignInterface().signMessage(bytesBalance1, closingParticipantCredential.getEcKeyPair());
        byte[] participantSignBalance1 = signatureDataToBytes(sigDataBalance1);
     // byte[] participantSignBalance1 = Util.hexStringToBytes(channelCloseReq.getClosingSignature());

        TransactionReceipt t  = tokenNetwork.closeChannel(channelIdentifier ,nonClosingParticipant, closingParticipant, balance_hash_non_closing, nonce, addtional_hash ,participantSignBalance2,participantSignBalance1).send();
       log.info("***********" + t.getStatus());
//        log.info("******" + DecodeOutputUtils.decodeOutputReturnString0x16(t.getOutput()));
        TokenNetwork.ChannelClosedEventResponse  closeEvent = tokenNetwork.getChannelClosedEvents(t).get(0);

        dealWithReceipt(t);
        return true;
    }

    public Boolean updateNonClosingBalanceProof(UpdateBalanceProofReq updateBalanceProofReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {
        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);
        BigInteger nonce = updateBalanceProofReq.getClosingParticipantNonce();
        BigInteger channelIdentifier =   updateBalanceProofReq.getChannelIdentifier();

        String closingParticipant = updateBalanceProofReq.getClosingParticipant();
        String nonClosingParticipant = updateBalanceProofReq.getNonClosingParticipant();
        BigInteger particantClosingBalance = updateBalanceProofReq.getClosingParticipantBalance();
        // lock is not use
        BigInteger lockBalance = new BigInteger("0");
        byte[] lockroot =  new byte[32];
       // byte[] balance_hash_closing = CryptoUtil.soliditySha3( particantClosingBalance,lockBalance,lockroot);
        byte[] addtional_hash = new byte[32];

        byte[]   balance_hash_closing =  Util.hexStringToBytes(updateBalanceProofReq.getBalanceHashClosing());
//        byte[] bytesBalanceClosing = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id, MessageTypeId.BalanceProof ,chain_id,balance_hash_closing, nonce , addtional_hash);
//        Credentials closingParticipantCredential = keyStoreService.getCredentials(closingParticipant,false );
//        Sign.SignatureData sigDataBalanceClosing = Sign.getSignInterface().signMessage(bytesBalanceClosing, closingParticipantCredential.getEcKeyPair());
//        byte[] participantSignBalanceClosing = signatureDataToBytes(sigDataBalanceClosing);
         byte[] participantSignBalanceClosing = Util.hexStringToBytes(updateBalanceProofReq.getClosingSignature());

        byte[] bytesBalanceNonClosing = CryptoUtil.solidityBytes( new Address(tokenNetworkAddress),chain_id, MessageTypeId.BalanceProofUpdate,channelIdentifier, balance_hash_closing, nonce, addtional_hash, participantSignBalanceClosing);
        Credentials nonClosingParticipantCredential = keyStoreService.getCredentials(closingParticipant,false );

        Sign.SignatureData sigDataBalanceNonClosing = Sign.getSignInterface().signMessage(bytesBalanceNonClosing, nonClosingParticipantCredential.getEcKeyPair());
        byte[] participantSignBalanceNonClosing = signatureDataToBytes(sigDataBalanceNonClosing);
    //     byte[] participantSignBalanceNonClosing = Util.hexStringToBytes(updateBalanceProofReq.getNonClosingnature());


        tokenNetwork.updateNonClosingBalanceProof(channelIdentifier,closingParticipant,nonClosingParticipant,balance_hash_closing,nonce,addtional_hash,participantSignBalanceClosing,participantSignBalanceNonClosing).send();
        return true;

    }


    public Boolean settle(ChannelSettleReq channelSettleReq, int groupId, String userAddress, String tokenNetworkAddress) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, userAddress, tokenNetworkAddress);
        BigInteger channelIdentifier = channelSettleReq.getChannelIdentifier();
        String participant1 = channelSettleReq.getParticipant1();
        String participant2 =   channelSettleReq.getParticipant2();
        byte[] lockroot =  new byte[32];
        BigInteger participant1TransferredAmount = channelSettleReq.getParticipant1TransferredAmount();
        BigInteger participant2TransferredAmount = channelSettleReq.getParticipant2TransferredAmount();
        //checkout amount ;
       // participant1TransferredAmount.add(participant2TransferredAmount).intValue() = 100;
        BigInteger participant1LockedAmount = new BigInteger("0");
        tokenNetwork.settleChannel(channelIdentifier,participant1,participant1TransferredAmount,participant1LockedAmount, lockroot, participant2,participant2TransferredAmount,participant1LockedAmount, lockroot).send();
        return true;

    }



    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {

        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            throw new FrontException(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
        if (!"0x0".equals(transactionReceipt.getStatus())) {
            throw new FrontException(Thread.currentThread().getStackTrace()[2].getMethodName() + " 交易失败,状态：" + transactionReceipt.getStatus() + "交易返回 " + transactionReceipt.getOutput());
        }
    }


    private Exchange getExchangeBAC001(int groupId, String userAddress, String exchangeContractAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return Exchange.load(exchangeContractAddress, web3j, credentials, Constants.contractGasProvider);
    }

    private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );
        return BAC001.load(assetAddress, web3j, credentials, Constants.contractGasProvider);
    }

    private TokenNetwork getTokenNetwork(int groupId, String userAddress, String assetAddress) {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials;
        if(userAddress.equals(credentialsObserver.getAddress())) {
            credentials =  credentialsObserver;
        } else {
            credentials  = keyStoreService.getCredentials(userAddress,false );
        }

        return TokenNetwork.load(assetAddress, web3j, credentials, Constants.contractGasProvider);
    }








    static public  byte[]  signatureDataToBytes(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  byte_3;
    }


    public String createBACNetwork(int groupId, String userAddress, String assetAddress) throws Exception {

          Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress,false );

       HTLCInfo htlcInfo =  htlcInfoService.findByGroupId(groupId);
       String tokenNetworkRegistryAddress =  htlcInfo.getTokenNetworkRegistry();
         TokenNetworkRegistry tokenNetworkRegistry = TokenNetworkRegistry.load(tokenNetworkRegistryAddress,web3j, credentials, contractGasProvider);

         //settle time
        TransactionReceipt t = tokenNetworkRegistry.createERC20TokenNetwork(assetAddress,new BigInteger("1000000000000000000000000"),new BigInteger("1000000000000000000000000")).send();

        List<TokenNetworkRegistry.TokenNetworkCreatedEventResponse> responses =  tokenNetworkRegistry.getTokenNetworkCreatedEvents(t);
        String token_network_address = responses.get(0).token_network_address;
       log.info("token_network_address: " + token_network_address);
       return token_network_address;

    }



    public TransferLog transfer(TransferLog transfer, String userAddress, String bacNetworkAddress) {

        String from = transfer.getFromAddress();
        String participant1 = transfer.getParticipant1();
        BigInteger fromBalance ;
        if(from.equals(participant1)){
            fromBalance = transfer.getParticipant1Balance();
        }
        else {
            fromBalance = transfer.getParticipant2Balance();
        }

        Credentials credentials = keyStoreService.getCredentials(from,false );

        BigInteger lockBalance = new BigInteger("0");
        byte[] lockroot =  new byte[32];
        byte[] addtional_hash =  new byte[32];
        byte[] balance_hash_from= CryptoUtil.soliditySha3( fromBalance,lockBalance,lockroot);
       //Util.hexStringToBytes
        BigInteger nonce = transfer.getNonce();
        byte[] bytesBalance2 = CryptoUtil.solidityBytes( new Address(bacNetworkAddress),chain_id, MessageTypeId.BalanceProof.getValue(), transfer.getChannelIdentifier(),balance_hash_from,nonce, addtional_hash);

        Sign.SignatureData sigDataBalance2 = Sign.getSignInterface().signMessage(bytesBalance2, credentials.getEcKeyPair());
        byte[] participantSignBalance2 = signatureDataToBytes(sigDataBalance2);

        transfer.setFromBalanceHash(Util.byteToHex(balance_hash_from));
        transfer.setFromSignature( Util.byteToHex(participantSignBalance2));
        transfer.setCreateTime(LocalDateTime.now());
        return transferLogRepository.save(transfer);

    }

    public Boolean confirm(Long tranferLogId) {

     TransferLog transferLog =    transferLogRepository.findOne(tranferLogId);
        transferLog.setStatus(false);

        transferLogRepository.save(transferLog);
        return true;
    }

    public Map<String, Object> getChannelInfo(String participant1, String participant2, BigInteger channelIdentifier, String bacNetworkAddress, int groupId) throws Exception {

        TokenNetwork tokenNetwork =  getTokenNetwork(groupId, credentialsObserver.getAddress(), bacNetworkAddress);
        Tuple2<BigInteger, BigInteger> tuple2 =
                tokenNetwork.getChannelInfo(channelIdentifier,participant1,participant2 ).send();

        Map<String,Object> imap = new HashMap<>();

        imap.put("settle_block_number", tuple2.getValue1());
        imap.put("state", tuple2.getValue2());
        return  imap;

    }
}
