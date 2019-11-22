package com.webank.webase.front.trade.raiden.translog;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferLogRepository extends CrudRepository<TransferLog, Long> ,JpaSpecificationExecutor<TransferLog> {



}
