package com.webank.webase.front.trade.raiden.network;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkRepository extends CrudRepository<Network, Long> ,JpaSpecificationExecutor<Network> {



}
