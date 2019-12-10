package com.webank.webase.front.trade.txspeed.translog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

@Service
    public class TransferLogService {

    @Autowired
    TransferLogRepository transferLogRepository;

    public void save(TransferLog transferLog) {
        transferLogRepository.save(transferLog);
    }


    public Page<TransferLog> getTransferLog(int pageNumber, int pageSize,String bacNetworkAddress, BigInteger channelIdentifier) {
        Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "createTime");
        Page<TransferLog> ordersPage = transferLogRepository.findAll(
                (Root<TransferLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            //        Predicate predicate = FrontUtils.buildPredicate(root, criteriaBuilder, status);
//                    query.where(predicate);
//                    return query.getRestriction();
                    //添加断言
                  Predicate predicate1 = criteriaBuilder.equal(root.get("channelIdentifier").as(BigInteger.class),channelIdentifier);
                  Predicate predicate2=    criteriaBuilder.equal(root.get("bacNetworkAddress").as(String.class), bacNetworkAddress);                                    ;
                  return  criteriaBuilder.and(predicate1,predicate2);
                  },  pageable);
        return ordersPage;

    }


    public TransferLog getTransferLogLatest(String bacNetworkAddress, BigInteger channelIdentifier) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<TransferLog> ordersList = transferLogRepository.findAll(
                (Root<TransferLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            //        Predicate predicate = FrontUtils.buildPredicate(root, criteriaBuilder, status);
//                    query.where(predicate);
//                    return query.getRestriction();
                    //添加断言
                  Predicate predicate1 = criteriaBuilder.equal(root.get("channelIdentifier").as(BigInteger.class),channelIdentifier);
                  Predicate predicate2=    criteriaBuilder.equal(root.get("bacNetworkAddress").as(String.class), bacNetworkAddress);                                    ;
                  return  criteriaBuilder.and(predicate1,predicate2);
                  },sort);
        return ordersList.get(0);

    }

}