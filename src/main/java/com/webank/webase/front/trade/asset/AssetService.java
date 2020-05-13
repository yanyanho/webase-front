package com.webank.webase.front.trade.asset;

import com.webank.webase.front.base.Constants;
import com.webank.webase.front.base.FrontUtils;
import com.webank.webase.front.base.enums.AssetStatus;
import com.webank.webase.front.base.exception.FrontException;
import com.webank.webase.front.keystore.KeyStoreService;
import com.webank.webase.front.trade.polo.BAC001;
import com.webank.webase.front.trade.polo.BAC002;
import com.webank.webase.front.trade.request.IssueReq;
import com.webank.webase.front.trade.request.SendReq;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private AssetRepository assetRepository;

    public BigDecimal assetBalance(String contractName, String contractAddress, String userAddress, int groupId) throws FrontException {
        BigDecimal balance = new BigDecimal("0");
        if (!userAddress.equals("0x0000000000000000000000000000000000000000")) {
            try {
                if ("BAC001".equals(contractName)) {
                    BAC001 bac001 = getBAC001OnlyQueryChain(groupId, contractAddress);
                    BigInteger minUnit = bac001.minUnit().send();
                    BigInteger unit = BigInteger.valueOf((long) Math.pow(10, minUnit.doubleValue()));
                    BigInteger userBalance = bac001.balance(userAddress).send();
                    balance = new BigDecimal(userBalance).divide(new BigDecimal(unit), minUnit.intValue(), RoundingMode.HALF_EVEN);

                } else {
                    BAC002 bac002 = getBAC002OnlyQueryChain(groupId, contractAddress);
                    balance = new BigDecimal(bac002.balance(userAddress).send());
                }
            } catch (Exception e) {
                log.error("assetBalance assetId:{} Exception", contractName, e);
                throw new FrontException(e.getMessage());
            }
        }
        return balance;

    }


    private BAC001 getBAC001(int groupId, String userAddress, String assetAddress) throws IOException {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress, false);
        BAC001 bac001 = BAC001.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac001.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//            }
        return bac001;
    }

    private BAC001 getBAC001OnlyQueryChain(int groupId, String assetAddress) throws IOException {
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
        Credentials credentials = keyStoreService.getCredentials(userAddress, false);
        BAC002 bac002 = BAC002.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac002.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//            }
        return bac002;
    }

    private BAC002 getBAC002OnlyQueryChain(int groupId, String assetAddress) throws IOException {
        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = Credentials.create("2");
        BAC002 bac002 = BAC002.load(assetAddress, web3j, credentials, contractGasProvider);
//            if(!bac002.isValid()) {
//                throw new FrontException("contract load failed , please checkout contract address!");
//
//            }
        return bac002;
    }


    public BACInfo assetInfo(String contractName, String contractAddress, int groupId) throws Exception {
        if ("BAC001".equals(contractName)) {
            BAC001 bac001 = getBAC001OnlyQueryChain(groupId, contractAddress);
            String description = bac001.description().send();
            BigInteger totalAmount = bac001.totalAmount().send();
            BigInteger minUnit = bac001.minUnit().send();
            String shortName = bac001.shortName().send();
            Boolean status = bac001.suspended().send();
            return new BACInfo(description, totalAmount, minUnit, shortName, status, contractAddress, contractName, groupId);
        } else {
            BAC002 bac002 = getBAC002OnlyQueryChain(groupId, contractAddress);
            String description = bac002.description().send();
            BigInteger totalAmount = bac002.totalSupply().send();
            String shortName = bac002.shortName().send();
            Boolean status = bac002.suspended().send();
            return new BACInfo(description, totalAmount, new BigInteger("0"), shortName, status, contractAddress, contractName, groupId);
        }
    }

    public Boolean sendFund(SendReq sendReq, String contractName, String contractAddress, int groupId) throws Exception {

        if ("BAC001".equals(contractName)) {
            BAC001 bac001 = getBAC001(groupId, sendReq.getFrom(), contractAddress);
            String to = sendReq.getTo();
            String data = sendReq.getData();
            BigInteger minUnit = sendReq.getMinUnit();
            if (sendReq.getMinUnit() == null) {
                minUnit = bac001.minUnit().send();
            }
            BigInteger realAmount = BigDecimal.valueOf(Math.pow(10, minUnit.doubleValue())).multiply(sendReq.getValue()).toBigInteger();
            TransactionReceipt transactionReceipt = bac001.send(to, realAmount, data.getBytes()).send();
            dealWithReceipt(transactionReceipt);
            return true;
        } else {
            BAC002 bac002 = getBAC002(groupId, sendReq.getFrom(), contractAddress);
            TransactionReceipt transactionReceipt = bac002.sendFrom(sendReq.getFrom(), sendReq.getTo(), sendReq.getAssetId(), sendReq.getData().getBytes()).send();
            dealWithReceipt(transactionReceipt);
        }
        return true;
    }

    public String issueAsset(IssueReq issueReq, String contractName, String userAddress, int groupId) throws Exception {

        //save asset
        AssetDO assetDO = saveAsset(issueReq, contractName, userAddress, groupId);

        Web3j web3j = web3jMap.get(groupId);
        Credentials credentials = keyStoreService.getCredentials(userAddress, false);
        try {
            String assetAddress;
            if ("BAC001".equals(contractName)) {
                BAC001 bac001 = BAC001.deploy(web3j, credentials, Constants.contractGasProvider, issueReq.getDescription(), issueReq.getShortName(), issueReq.getMinUnit(), issueReq.getTotalAmount()).send();
                assetAddress = bac001.getContractAddress();
            } else if ("BAC002".equals(contractName)) {
                BAC002 bac002 = BAC002.deploy(web3j, credentials, Constants.contractGasProvider, issueReq.getDescription(), issueReq.getShortName()).send();
                assetAddress = bac002.getContractAddress();
            } else {
                throw new FrontException("invalid contractName");
            }

            //save address of asset
            assetRepository.save(assetDO.setAssetAddress(assetAddress));
            return assetAddress;
        } catch (Exception e) {
            assetRepository.delete(assetDO.getAssetId());
            throw new FrontException(e.getMessage());
        }
    }


    /**
     * 保存新的资产信息
     */
    public AssetDO saveAsset(IssueReq issueReq, String contractName, String userAddress, int groupId) {
        Assert.notNull(issueReq, "Asset information cannot be empty");
        Assert.notNull(issueReq.getShortName(), "shortName cannot be empty");
        BigInteger minUnit = null;
        if ("BAC001".equals(contractName)) {
            Assert.notNull(issueReq.getTotalAmount(), "totalAmount cannot be empty");
            minUnit = issueReq.getMinUnit() == null ? BigInteger.valueOf(0) : issueReq.getMinUnit();
        }

        if(issueReq.getTotalAmount().compareTo(new BigInteger("7fffffffffffffff",16))>0) {
            throw new FrontException("totalAmount must less than 0x7fffffffffffffff");
        }
        AssetDO assetDO = new AssetDO();
        assetDO.setContractName(contractName)
                .setAssetGroup(groupId)
                .setOwner(userAddress)
                .setShortName(issueReq.getShortName())
                .setTotalAmount(issueReq.getTotalAmount())
                .setMinUnit(minUnit)
                .setAssetStatus(AssetStatus.normal.getValue())
                .setDescription(issueReq.getDescription())
                .setCreateTime(LocalDateTime.now())
                .setModifyTime(LocalDateTime.now());

        return assetRepository.save(assetDO);
    }

    /**
     * find contract by page.
     */
    public Page<AssetDO> findAssetByPage(ReqPageAsset param) {
        Pageable pageable = new PageRequest(param.getPageNumber(), param.getPageSize(),
                Sort.Direction.DESC, "modifyTime");
        Page<AssetDO> contractPage = assetRepository.findAll(
                (Root<AssetDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                    Predicate predicate = FrontUtils.buildPredicate(root, criteriaBuilder, param);
                    query.where(predicate);
                    return query.getRestriction();
                }, pageable);
        return contractPage;

    }

    public List<AssetDO> findDefaultAsset() {
        Sort sort = new Sort(Sort.Direction.DESC, "shortName");

        List<AssetDO> assetList = assetRepository.findAll(
                (Root<AssetDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

                    Predicate predicate1 =   criteriaBuilder.equal(root.get("shortName").as(String.class),"AAA-DEMO");
                    Predicate predicate2 =   criteriaBuilder.equal(root.get("shortName").as(String.class),"BBB-DEMO");

                    return criteriaBuilder.or(predicate1,predicate2);
                }, sort);
        return assetList;


    }



    public List<AssetDO> findAssetByShortName(String shortName) {

        return  assetRepository.findByShortNameLike((shortName).toUpperCase());
    }


}
