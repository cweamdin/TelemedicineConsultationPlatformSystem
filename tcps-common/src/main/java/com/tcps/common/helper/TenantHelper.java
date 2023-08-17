package com.tcps.common.helper;

import cn.hutool.core.convert.Convert;
import com.tcps.common.utils.spring.SpringUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 租户缓存
 * @author Tao Guang
 */
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TenantHelper {

    /**
     * 租户功能是否启用
     */
    public static boolean isEnable() {
        return Convert.toBool(SpringUtils.getProperty("tenant.enable"), false);
    }


    /**
     * 获取租户ID
     */
    public static String getTenantId() {
        return LoginHelper.getTenantId();
    }

}
