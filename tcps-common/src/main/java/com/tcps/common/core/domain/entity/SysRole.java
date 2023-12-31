package com.tcps.common.core.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tcps.common.annotation.ExcelDictFormat;
import com.tcps.common.constant.UserConstants;
import com.tcps.common.convert.ExcelDictConvert;
import com.tcps.common.core.domain.BaseEntity;
import com.tcps.common.core.domain.model.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 *
 * @author Tao Guang
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
@ExcelIgnoreUnannotated
public class SysRole extends TenantEntity {

    /**
     * 角色ID
     */
    @ExcelProperty(value = "角色序号")
    @TableId(value = "role_id")
    private Long roleId;

    /**
     * 归属机构
     */
    private String officeId;

    /**
     * 角色名称
     */
    @ExcelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;

    @ExcelProperty(value = "角色英文名称")
    @NotBlank(message = "角色英文名称不能为空")
    @Size(min = 0, max = 30, message = "角色英文名称长度不能超过{max}个字符")
    /**
     * 英文名称
     */
    private String roleEnName;

    /**
     * 角色权限
     */
    @ExcelProperty(value = "角色权限")
    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过{max}个字符")
    private String roleKey;

    /**
     * 角色排序
     */
    @ExcelProperty(value = "角色排序")
    @NotNull(message = "显示顺序不能为空")
    private Integer roleSort;

    /**
     * 角色类型
     */
    @NotNull(message = "显示类型不能为空")
    private String roleType;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    @ExcelProperty(value = "数据范围", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    private Boolean officeCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    @ExcelProperty(value = "角色状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * 是否系统数据(1是 0不是)
     */
    private String sys;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @TableField(exist = false)
    private boolean flag = false;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    @TableField(exist = false)
    private Long[] officeIds;

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return UserConstants.ADMIN_ID.equals(this.roleId);
    }
}
