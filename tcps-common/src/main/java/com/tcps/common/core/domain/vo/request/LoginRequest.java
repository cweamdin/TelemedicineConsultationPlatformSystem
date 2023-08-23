package com.tcps.common.core.domain.vo.request;

import com.tcps.common.constant.UserConstants;
import com.tcps.common.core.validate.PasswordGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录请求
 * @author Tao Guang
 */
@Data
public class LoginRequest {

    /**
     * 授权类型(登录方式)
     */
    @NotNull(message = "{auth.grant.type.not.blank}")
    private Integer grantType;

    /**
     * 租户ID
     */
    @NotNull(message = "{tenant.number.not.blank}")
    private String tenantId;

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}", groups = {PasswordGroup.class})
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = "{user.username.length.valid}", groups = {PasswordGroup.class})
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}", groups = {PasswordGroup.class})
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}", groups = {PasswordGroup.class})
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
