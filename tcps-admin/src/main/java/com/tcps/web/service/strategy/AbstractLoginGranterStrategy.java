package com.tcps.web.service.strategy;

import com.tcps.web.domain.vo.request.LoginRequest;
import com.tcps.web.domain.vo.response.LoginResponse;
import com.tcps.common.enums.GranterTypeEnum;
import com.tcps.web.service.factory.LoginGranterFactory;

import javax.annotation.PostConstruct;

/**
 * 抽象登录授权处理器
 * @author  Tao Guang
 */

public abstract class AbstractLoginGranterStrategy {

    /**
     * 初始化授权类型(注册到工厂时调用)
     */
    @PostConstruct
    private void init() {
        LoginGranterFactory.register(getLoginTypeEnum().getType(), this);
    }

    abstract GranterTypeEnum getLoginTypeEnum();

    /**
     * 参数校验
     */
    public abstract void validate(LoginRequest loginRequest);

    /**
     * 登录
     */
    public abstract LoginResponse login(LoginRequest loginRequest);


}
