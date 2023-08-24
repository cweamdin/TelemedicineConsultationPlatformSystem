package com.tcps.system.service;

import com.tcps.system.domain.bo.SysTenantBo;
import com.tcps.common.core.domain.vo.SysTenantVo;

import java.util.List;

/**
 * 租户Service接口
 * @author Tao Guang
 */
public interface ISysTenantService {

    /**
     * 基于租户ID查询租户
     */
    SysTenantVo queryByTenantId(String tenantId);

    List<SysTenantVo> queryList(SysTenantBo sysTenantBo);


    /**
     * * 医院开户
     * @param sysTenantVo
     */
    void insertTenant(SysTenantVo sysTenantVo);
}
