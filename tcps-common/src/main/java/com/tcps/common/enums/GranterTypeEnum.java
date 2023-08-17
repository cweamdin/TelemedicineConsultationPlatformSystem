package com.tcps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 授权类型枚举
 * @author Tao Guang
 */
@Getter
@AllArgsConstructor
public enum GranterTypeEnum {

    PASSWORD(1,"password","user.password.retry.limit.exceed", "user.password.retry.limit.count"),
    CAPTCHA(2,"captcha","user.password.retry.limit.exceed", "user.password.retry.limit.count"),
    MOBILE(3,"mobile","",""),
    OPENID(4,"social","",""),
    EMAIL(5,"email","",""),;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 登录重试超出限制提示
     */
    private final String retryLimitExceed;

    /**
     * 登录重试限制计数提示
     */
    private final String retryLimitCount;

    private static Map<Integer, GranterTypeEnum> cache;

    static {
        cache = Arrays.stream(GranterTypeEnum.values()).collect(Collectors.toMap(GranterTypeEnum::getType, Function.identity()));
    }

    public static GranterTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
