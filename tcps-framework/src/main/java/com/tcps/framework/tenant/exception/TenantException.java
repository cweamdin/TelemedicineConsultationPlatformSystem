package com.tcps.framework.tenant.exception;

import com.tcps.common.exception.base.BaseException;

/**
 * 租户异常类
 * @author Tao Guang
 */
public class TenantException extends BaseException {

    private static final long serialVersionUID = 1L;

    public TenantException(String code, Object... args) {
        super("tenant", code, args, null);
    }

}
