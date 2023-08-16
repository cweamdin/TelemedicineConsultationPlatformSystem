package com.tcps.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author Tao Guang
 */

@Data
@TableName("sys_role_Office")
public class SysRoleOffice {

    /**
     * 角色ID
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;

    /**
     * 部门ID
     */
    private Long officeId;

}
