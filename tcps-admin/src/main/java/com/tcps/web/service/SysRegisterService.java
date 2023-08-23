package com.tcps.web.service;

import com.tcps.common.constant.Constants;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.core.domain.event.LogininforEvent;
import com.tcps.common.enums.UserType;
import com.tcps.common.exception.user.UserException;
import com.tcps.common.utils.MessageUtils;
import com.tcps.common.utils.ServletUtils;
import com.tcps.common.utils.spring.SpringUtils;
import com.tcps.framework.config.properties.CaptchaProperties;
import com.tcps.system.service.ISysUserService;
import com.tcps.web.domain.vo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统注册服务
 */
@RequiredArgsConstructor
@Service
public class SysRegisterService {

    private final ISysUserService userService;
    private final CaptchaProperties captchaProperties;

    /**
     * 注册
     */
    public void register(RegisterRequest registerRequest) {
        String tenantId = registerRequest.getTenantId();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String userType = UserType.getUserType(registerRequest.getUserType()).getUserType();

        boolean captchaEnabled = captchaProperties.getEnabled();
        SysUser user = new SysUser();
        user.setUserName(username);
        user.setPassword(password);
        user.setUserType(userType);

        if (!userService.checkUserNameUnique(user)) {
            throw new RuntimeException("user.register.save.error");
        }

        boolean regFlag = userService.registerUser(user, tenantId);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }

        recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.register.success"));

    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    private void recordLogininfor(String tenantId,String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }
}
