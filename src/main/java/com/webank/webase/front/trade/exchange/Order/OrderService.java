package com.webank.webase.front.trade.exchange.Order;

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
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
    public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void save(ExchangeOrder exchangeOrder) {
        orderRepository.save(exchangeOrder);
    }


    public Page<ExchangeOrder> getAvailableOrder(int pageNumber, int pageSize, int status) {
        Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "updateTime");
        Page<ExchangeOrder> ordersPage = orderRepository.findAll(
                (Root<ExchangeOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            //        Predicate predicate = FrontUtils.buildPredicate(root, criteriaBuilder, status);
//                    query.where(predicate);
//                    return query.getRestriction();
                    //添加断言
                  Predicate predicate = criteriaBuilder.equal(root.get("status").as(Integer.class),status);
                  return predicate;
                  },  pageable);
        return ordersPage;

    }


    public ExchangeOrder findById(String orderHash) {
        return orderRepository.findOne(orderHash);
    }


    /**
     * 将过期时间小于当前时间的订单设置为超时
     */
    @Transactional
    public void updateExpireOrder() {
        Long currentMilli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        orderRepository.updateExpireOrder(BigInteger.valueOf(currentMilli));
    }
}