package com.webank.webase.front.trade.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository of asset.
 */
@Repository
public interface AssetRepository extends
        JpaRepository<AssetDO, String>, JpaSpecificationExecutor<AssetDO> {

    @Query(value = "select t from tb_asset  t where upper(t.shortName) like %?1% or upper(t.assetAddress)=?1")
    List<AssetDO> findByShortNameLike(String name);
}
