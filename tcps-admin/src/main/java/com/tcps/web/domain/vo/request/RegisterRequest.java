package com.tcps.web.domain.vo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注册对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterRequest extends LoginRequest {

    /**
     * 用户类型
     */
    private String userType;

}
