package com.webank.webase.front.trade.exchange.Order;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends CrudRepository<Order,String >, JpaSpecificationExecutor<Order> {


}