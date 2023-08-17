package com.tcps.common.core.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcps.common.annotation.Sensitive;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.core.domain.entity.SysRole;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.enums.SensitiveStrategy;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户信息视图对象 (VO)
 */
@Data
@AutoMapper(target = SysUser.class)
public class SysUserVo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 租户ID
     */
    private String tenantId;

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
    private String userName;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
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
    @JsonIgnore
    @JsonProperty
    private String password;


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
     * 机构对象
     */
    private SysOffice office;

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 数据权限 当前角色ID
     */
    private Long roleId;

}
