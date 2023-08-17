package com.tcps.framework.tenant.handler;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.tcps.common.helper.LoginHelper;
import com.tcps.common.utils.StringUtils;
import com.tcps.framework.tenant.properties.TenantProperties;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义租户处理器
 */
@AllArgsConstructor
public class PlusTenantLineHandler implements TenantLineHandler {

    private final TenantProperties tenantProperties;

    @Override
    public Expression getTenantId() {
        String tenantId = LoginHelper.getTenantId();
        if (StringUtils.isBlank(tenantId)) {
            return new NullValue();
        }
        // todo 动态租户后续加上
        // 返回固定租户
        return  new StringValue(tenantId);
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // todo 判断是否有租户 获取租户id
        String tenantId = LoginHelper.getTenantId();
        // 判断是否有租户
        if (StringUtils.isNotBlank(tenantId)) {
            // 不需要过滤租户的表
            List<String> excludes = tenantProperties.getExcludes();
            // 非业务表
            ArrayList<String> tables = ListUtil.toList(
                "gen_table",
                "gen_table_column"
            );
            tables.addAll(excludes);
            return excludes.contains(tableName);
        }
        return true;
    }
}
