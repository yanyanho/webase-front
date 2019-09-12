package com.webank.webase.front.trade.exchange.Order;

import com.webank.webase.front.base.FrontUtils;
import com.webank.webase.front.contract.entity.Contract;
import com.webank.webase.front.contract.entity.ReqPageContract;
import com.webank.webase.front.trade.trade.htlc.HTLCInfo;
import com.webank.webase.front.trade.trade.htlc.HTLCInfoRepository;
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


    public void save(Order order) {
        orderRepository.save(order);
    }

    // public void update() {orderRepository.}


 //   public Page<Order> getAvailableOrder(int pageNumber, int pageSize, int status) {
//        Pageable pageable = new PageRequest(pageNumber, pageSize,
//                Sort.Direction.DESC, "updateTime");
//        Page<Order> ordersPage = orderRepository.findAll(
//                (Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
//                    Predicate predicate = FrontUtils.buildPredicate(root, criteriaBuilder, status);
//                    query.where(predicate);
//                    return query.getRestriction();
//                }, pageable);
//        return ordersPage;
        //  orderRepository.findAll();

 //   }
}