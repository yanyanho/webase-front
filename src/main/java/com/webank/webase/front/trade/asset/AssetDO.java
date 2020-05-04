package com.webank.webase.front.trade.asset;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_asset")
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_asset")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AssetDO implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "asset_id", columnDefinition = "varchar(32) comment '资产编号'")
    private String assetId;
    @Column(name = "contract_name", columnDefinition = "varchar(25)  not null comment '合约名称:BAC001、BAC002'")
    private String contractName;
    @Column(name = "asset_address", columnDefinition = "varchar(66) default null comment '资产合约地址'")
    private String assetAddress;
    @Column(name = "asset_group", columnDefinition = "int(11) not null comment '所属群组'")
    private Integer assetGroup;
    @Column(name = "asset_status", columnDefinition = "char(1) not null default '1' comment '资产状态 1正常，2暂停，3销毁'")
    private Integer assetStatus;
    @Column(name = "short_name", columnDefinition = "varchar(65)  not null comment '资产简称'")
    private String shortName;
    @Column(name = "min_uint", columnDefinition = "bigint default null comment '资产精度'")
    private BigInteger minUnit;
    @Column(name = "total_amount", columnDefinition = "bigint not null comment '资产总额'")
    private BigInteger totalAmount;
    @Column(name = "description", columnDefinition = "text default null comment '备注'")
    private String description;
    @Column(name = "owner", columnDefinition = "varchar(66) not null comment '资产拥有者'")
    private String owner;

    @Column(name = "create_time", columnDefinition = "TIMESTAMP not null comment '创建时间'")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "modify_time", columnDefinition = "TIMESTAMP  not null comment '最近修改时间'")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;
}
