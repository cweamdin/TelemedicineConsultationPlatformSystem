package com.tcps.web.service.factory;

import com.tcps.web.service.strategy.AbstractLoginGranterStrategy;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;

import java.util.Map;

/**
 * 登录授权工厂
 *
 * @author Tao Guang
 */
public class LoginGranterFactory {

    /**
     * 登录授权Map
     */
    public static final Map<Integer, AbstractLoginGranterStrategy> LOGIN_GRANTER_MAP = new ConcurrentHashMap<>();

    /**
     * 注册登录授权
     *
     * @param loginType 登录授权类型
     * @param granter   登录授权
     */
    public static void register(Integer loginType, AbstractLoginGranterStrategy granter) {
        LOGIN_GRANTER_MAP.put(loginType, granter);
    }

    /**
     * 获取登录授权
     *
     * @param code 登录授权类型 code 码
     * @return 登录授权
     */
    public static AbstractLoginGranterStrategy getStrategyNoNull(Integer code) {
        // 从map中获取对应的策略处理器
        AbstractLoginGranterStrategy abstractLoginGranterStrategy = LOGIN_GRANTER_MAP.get(code);
        // 校验策略处理器是否存在 TODO 不存在时，抛出异常
        return abstractLoginGranterStrategy;
    }

}
