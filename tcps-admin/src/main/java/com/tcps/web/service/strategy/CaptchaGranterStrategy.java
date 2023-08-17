package com.tcps.web.service.strategy;

import com.tcps.web.domain.vo.request.LoginRequest;
import com.tcps.web.domain.vo.response.LoginResponse;
import com.tcps.common.enums.GranterTypeEnum;
import com.tcps.framework.config.properties.CaptchaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 账号密码 + 验证码授权处理器
 */
@Component
@RequiredArgsConstructor
public class CaptchaGranterStrategy extends AbstractLoginGranterStrategy {

    private final CaptchaProperties captchaProperties;

    /**
     * 获取授权类型: 账号密码 + 验证码
     */
    @Override
    GranterTypeEnum getLoginTypeEnum() {
        return GranterTypeEnum.CAPTCHA;
    }

    /**
     * 参数校验
     */
    @Override
    public void validate(LoginRequest loginRequest) {

    }

    /**
     * 登录
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        String tenantId = loginRequest.getTenantId();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String code = loginRequest.getCode();
        String uuid = loginRequest.getUuid();

        boolean captchaEnabled = captchaProperties.getEnabled();
        // todo 验证码校验

        return null;
    }
}
