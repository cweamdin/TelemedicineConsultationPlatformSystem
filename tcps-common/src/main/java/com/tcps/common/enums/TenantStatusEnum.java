package com.tcps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租户状态枚举
 */
@Getter
@AllArgsConstructor
public enum TenantStatusEnum {

    /**
     * 正常
     */
    OK("0", "正常"),
    /**
     * 停用
     */
    DISABLE("1", "停用");

    private final String code;
    private final String info;



}
