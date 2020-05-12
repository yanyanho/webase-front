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

    @Query(value = "select t from TB_ASSET  t where t.short_name like %?1%")
    List<AssetDO> findByShortNameLike(String name);
}
