package com.webank.webase.front.trade.trade.htlc;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HTLCInfoRepository extends CrudRepository<HTLCInfo, Long> {

    public HTLCInfo findByGroupId(int id);

}