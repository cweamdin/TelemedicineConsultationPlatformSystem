package com.tcps.common.core.domain.vo.response;

import com.tcps.common.core.domain.vo.SysTenantVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 租户列表
 *
 * @author Tao Guang
 */
@Data
@AutoMapper(target = SysTenantVo.class)
public class TenantResponseListVo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String companyName;

    /**
     * 租户域名
     */
    private String domain;

}
