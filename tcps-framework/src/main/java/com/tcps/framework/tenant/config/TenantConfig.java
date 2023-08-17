package com.tcps.framework.tenant.config;

import cn.dev33.satoken.dao.SaTokenDao;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.tcps.framework.config.MybatisPlusConfig;
import com.tcps.framework.config.RedisConfig;
import com.tcps.framework.tenant.dao.TenantSaTokenDao;
import com.tcps.framework.tenant.handler.PlusTenantLineHandler;
import com.tcps.framework.tenant.manager.TenantSpringCacheManager;
import com.tcps.framework.tenant.properties.TenantProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 * 1. 配置租户属性、必须在redis和mybatisPlus配置完成之后才能进行配置，租户必须启用
 * 2.
 * @author Tao Guang
 */
@EnableConfigurationProperties(TenantProperties.class)
@AutoConfiguration(after = {RedisConfig.class, MybatisPlusConfig.class})
@ConditionalOnProperty(value = "tenant.enable",havingValue = "true")
public class TenantConfig {

    /**
     * 初始化租户配置
     */
    @Bean
    public boolean tenantInit(MybatisPlusInterceptor mybatisPlusInterceptor,
                              TenantProperties tenantProperties) {

        List<InnerInterceptor> interceptors = new ArrayList<>();
        // 多租户插件必须放到第一位
        interceptors.add(tenantLineInnerInterceptor(tenantProperties));
        interceptors.addAll(mybatisPlusInterceptor.getInterceptors());
        mybatisPlusInterceptor.setInterceptors(interceptors);
        return true;
    }

    /**
     * 多租户插件
     */
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties tenantProperties) {
        return new TenantLineInnerInterceptor(new PlusTenantLineHandler(tenantProperties));
    }

    /**
     * 多租户缓存管理器
     */
    @Primary
    @Bean
    public CacheManager tenantCacheManager() {
        return new TenantSpringCacheManager();
    }


    /**
     * 多租户鉴权dao实现
     */
    @Primary
    @Bean
    public SaTokenDao tenantSaTokenDao() {
        return new TenantSaTokenDao();
    }



}
