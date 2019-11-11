package com.webank.webase.front.trade.exchange.Order;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderRepository extends CrudRepository<ExchangeOrder, String>, JpaSpecificationExecutor<ExchangeOrder> {


    @Modifying
    @Query("UPDATE ExchangeOrder SET status =3 WHERE status=0 and expires < ?1")
    void updateExpireOrder(@Param("currentMilli") BigInteger currentMilli);
}