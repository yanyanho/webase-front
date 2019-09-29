package com.webank.webase.front.trade.exchange.Order;

import com.webank.webase.front.base.FrontUtils;
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
import java.util.ArrayList;
import java.util.List;

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
       return  orderRepository.findOne(orderHash);
    }

}