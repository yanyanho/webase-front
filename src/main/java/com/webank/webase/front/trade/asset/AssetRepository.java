package com.webank.webase.front.trade.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * repository of asset.
 */
@Repository
public interface AssetRepository extends
        JpaRepository<AssetDO, String>, JpaSpecificationExecutor<AssetDO> {
}
