package com.webank.webase.front.trade.trade;


import com.webank.webase.front.keystore.KeyStoreInfo;
import com.webank.webase.front.monitor.Monitor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface HTLCInfoRepository extends CrudRepository<HTLCInfo, Long> {



}