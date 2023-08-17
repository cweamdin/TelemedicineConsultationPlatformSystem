package com.tcps.web.domain.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 登录租户对象
 *
 * @author Tao Guang
 */
@Data
public class LoginTenantResponse {


    /**
     * 租户开关
     */
    private Boolean tenantEnabled;

    /**
     * 租户对象列表
     */
    private List<TenantResponseListVo> voList;

}
