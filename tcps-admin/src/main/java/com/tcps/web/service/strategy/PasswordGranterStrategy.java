package com.tcps.web.service.strategy;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tcps.common.constant.Constants;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.core.domain.model.LoginUser;
import com.tcps.common.core.domain.vo.SysUserVo;
import com.tcps.common.core.domain.vo.request.LoginRequest;
import com.tcps.common.core.domain.vo.response.LoginResponse;
import com.tcps.common.enums.GranterTypeEnum;
import com.tcps.common.enums.UserStatus;
import com.tcps.common.exception.user.UserException;
import com.tcps.common.helper.LoginHelper;
import com.tcps.common.utils.MessageUtils;
import com.tcps.common.helper.TenantHelper;
import com.tcps.framework.config.properties.CaptchaProperties;
import com.tcps.system.mapper.SysUserMapper;
import com.tcps.web.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 密码授权处理器
 */
@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class PasswordGranterStrategy extends AbstractLoginGranterStrategy {

    private final CaptchaProperties captchaProperties;

    private final SysUserMapper sysUserMapper;

    private final SysLoginService loginService;

    /**
     * 获取授权类型: 账号密码
     */
    @Override
    GranterTypeEnum getLoginTypeEnum() {
        return GranterTypeEnum.PASSWORD;
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

        SysUserVo user = loadLoginBody(tenantId, username);
        // 校验密码
        loginService.checkLogin(getLoginTypeEnum(), tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));

        LoginUser loginUser = loginService.buildLoginUserInfo(user);
        // 生成token
        LoginHelper.login(loginUser);

        loginService.recordLoginInfo(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        loginService.recordLoginInfo(user.getUserId(), user.getUserName());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(StpUtil.getTokenValue());
        loginResponse.setExpireIn(StpUtil.getTokenTimeout());

        return loginResponse;
    }


    /**
     * 加载用户信息
     */
    private SysUserVo loadLoginBody(String tenantId, String username) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getUserName, SysUser::getTenantId)
            .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId).eq(SysUser::getUserName,username));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        SysUserVo sysUserVo = new SysUserVo();
        if (TenantHelper.isEnable()) {
            SysUser sysUser = sysUserMapper.selectTenantUserByUserName(username, tenantId);
            BeanUtils.copyProperties(sysUser,sysUserVo);
            return sysUserVo;
        }
        SysUserVo sysUser = sysUserMapper.selectUserByUserName(username);
        BeanUtils.copyProperties(sysUser,sysUserVo);
        return sysUserVo;
    }
}
