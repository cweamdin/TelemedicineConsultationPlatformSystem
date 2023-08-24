package com.tcps.web.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tcps.common.constant.Constants;
import com.tcps.common.constant.GlobalConstants;
import com.tcps.common.constant.TenantConstants;
import com.tcps.common.core.domain.dto.RoleDTO;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.core.domain.event.LogininforEvent;
import com.tcps.common.core.domain.model.LoginUser;
import com.tcps.common.core.domain.vo.SysUserVo;
import com.tcps.common.enums.*;
import com.tcps.common.exception.user.UserException;
import com.tcps.common.helper.LoginHelper;
import com.tcps.common.utils.DateUtils;
import com.tcps.common.utils.MessageUtils;
import com.tcps.common.utils.ServletUtils;
import com.tcps.common.utils.redis.RedisUtils;
import com.tcps.common.utils.spring.SpringUtils;
import com.tcps.common.helper.TenantHelper;
import com.tcps.framework.tenant.exception.TenantException;
import com.tcps.common.core.domain.vo.SysTenantVo;
import com.tcps.system.mapper.SysUserMapper;
import com.tcps.system.service.ISysConfigService;
import com.tcps.system.service.ISysTenantService;
import com.tcps.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

    private final SysUserMapper userMapper;
    private final ISysConfigService configService;
    private final SysPermissionService permissionService;
    private final ISysTenantService tenantService;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;


    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            recordLoginInfo(loginUser.getTenantId(),loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            StpUtil.logout();
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    public void recordLoginInfo(String tenantId ,String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }


    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginTime(DateUtils.getNowDate());
        sysUser.setUpdateBy(username);
        userMapper.updateById(sysUser);
    }

    /**
     * 校验租户
     *
     * @param tenantId 租户ID
     */
    public void checkTenant(String tenantId) {
        if (!TenantHelper.isEnable()) {
            return;
        }
        if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            return;
        }
        SysTenantVo sysTenantVo = tenantService.queryByTenantId(tenantId);
        if (Objects.isNull(sysTenantVo)) {
            log.info("登录租户：{} 不存在.", tenantId);
            throw new TenantException("租户不存在");
        }
        if (TenantStatusEnum.DISABLE.getCode().equals(sysTenantVo.getStatus())) {
            log.info("登录租户：{} 已被停用.", tenantId);
            throw new TenantException("租户已被停用");
        }
    }

    /**
     * 登录校验
     */
    public void checkLogin(GranterTypeEnum loginType, String tenantId, String username, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    /**
     *
     * @param user
     */
    public LoginUser buildLoginUserInfo(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setOfficeId(user.getOfficeId());
        loginUser.setUsername(user.getUserName());
        loginUser.setName(user.getName());
        loginUser.setUserType(user.getUserType());

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);

        loginUser.setMenuPermission(permissionService.getMenuPermission(sysUser));
        loginUser.setRolePermission(permissionService.getRolePermission(sysUser));
        loginUser.setOfficeName(ObjectUtil.isNull(user.getOffice()) ? "" : user.getOffice().getOfficeName());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }
}
