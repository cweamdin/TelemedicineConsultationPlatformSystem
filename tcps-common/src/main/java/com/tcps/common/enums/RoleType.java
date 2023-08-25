package com.tcps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * *<p>李太白</p>
 * *时间2023/8/24
 */
@Getter
@AllArgsConstructor
public enum RoleType {

    Hospital_Management_Role("hospital_management","hospital_admin","医院管理员","hospitaladmin"),

    Specialist_Role("specialist_management","specialist_type","专家","specialist"),

    Operation_Role("operation_management","operation_type","运营","operation"),

    Distribution_Role("distribution_management","distribution_type","分配员","distribution");
    ;

    //角色标识
    private final String roleKey;

    //角色类型
    private final String roleType;

    //角色名称
    private final String roleName;

    //角色名称英文
    private final String roleEName;

}
