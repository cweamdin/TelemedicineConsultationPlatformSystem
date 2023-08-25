package com.tcps.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tcps.common.core.domain.model.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 区域表
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("sys_area")
public class SysArea extends TenantEntity {

    /**
     * 编号
     */
    @TableId(value = "area_id")
    private Long areaId;
    private Long parentId;
    private String ancestors;
    private String areaName;
    private Long sort;
    private String code;
    private String areaType;
    private String remarks;
    private String delFlag;


}
