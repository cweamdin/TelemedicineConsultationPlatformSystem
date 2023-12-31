package com.tcps.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcps.common.annotation.Sensitive;
import com.tcps.common.constant.UserConstants;
import com.tcps.common.core.domain.model.TenantEntity;
import com.tcps.common.enums.SensitiveStrategy;
import com.tcps.common.xss.Xss;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author Tao Guang
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户 数据传输对象")
@TableName("sys_user")
public class SysUser extends TenantEntity {

    /**
     * 用户ID
     */
    @Schema(description = "ID")
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    /**
     * 归属公司
     */
    private Long companyId;

    /**
     * 归属机构部门
     */
    private Long officeId;

    /**
     * 用户账号
     */
    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过{max}个字符")
    private String userName;

    /**
     * 用户名称
     */
    @Xss(message = "用户名称不能包含脚本字符")
    @NotBlank(message = "用户名称不能为空")
    @Size(min = 0, max = 30, message = "用户名称长度不能超过{max}个字符")
    private String name;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    private String email;

    /**
     * 手机号码
     */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phone;

    private String no;

    /**
     * 职位
     */
    private String jobName;

    /**
     * 职级
     */
    private String jobLevel;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    @TableField(
        insertStrategy = FieldStrategy.NOT_EMPTY,
        updateStrategy = FieldStrategy.NOT_EMPTY,
        whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * todo 机构对象
     */
    @TableField(exist = false)
    private SysOffice office;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 数据权限 当前角色ID
     */
    @TableField(exist = false)
    private Long roleId;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return UserConstants.ADMIN_ID.equals(this.userId);
    }

}
