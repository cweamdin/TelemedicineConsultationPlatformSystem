package com.tcps.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.tcps.common.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 部门表 sys_coffice
 *
 * @author Tao Guang
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysOffice extends TreeEntity<SysOffice> {
    /**
     * 编号
     */
    @TableId(value = "office_id")
    private Long officeId;

    /**
     * 名称
     */
    @NotBlank(message = "机构名称不能为空")
    @Size(min = 0, max = 30, message = "机构名称长度不能超过{max}个字符")
    private String officeName;

    /**
     * 显示排序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 机构类型（1：医院；2：部门；3：小组）
     */
    private Integer type;

    /**
     * 机构等级（1：一级；2：二级；3：三级；4：四级）
     */
    private Integer grade;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 归属区域
     */
    private Long areaId;

    /**
     * 区域编码
     */
    private String code;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 电话
     */
    @Size(min = 0, max = 11, message = "联系电话长度不能超过{max}个字符")
    private String phone;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    private String email;

    /**
     * 是否启用(0正常,1停用)
     */
    private String status;

    /**
     * 主负责人
     */
    private Long primaryLeader;

    /**
     * 副负责人
     */
    private Long deputyLeader;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 账套
     */
    private String accountId;
}