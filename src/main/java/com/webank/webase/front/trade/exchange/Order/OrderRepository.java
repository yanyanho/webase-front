package com.webank.webase.front.trade.exchange.Order;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<ExchangeOrder,String >, JpaSpecificationExecutor<ExchangeOrder> {

    
}