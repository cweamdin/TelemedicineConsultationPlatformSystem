package com.tcps.common.enums;

import com.tcps.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备类型
 * 针对多套 用户体系
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * 管理人员用户
     */
    SYS_USER("sys_user"),
    /**
     * 医院用户(站点用户)
     */
    HOSPITAL_USER("hospital_user"),
    /**
     * 专家用户
     */
    EXPERT_USER("expert_user"),
    /**
     * 分配员用户
     */
    DISTRIBUTION_USER("distribution_user");



    private final String userType;

    public static UserType getUserType(String str) {
        for (UserType value : values()) {
            if (StringUtils.contains(str, value.getUserType())) {
                return value;
            }
        }
        throw new RuntimeException("'UserType' not found By " + str);
    }
}
