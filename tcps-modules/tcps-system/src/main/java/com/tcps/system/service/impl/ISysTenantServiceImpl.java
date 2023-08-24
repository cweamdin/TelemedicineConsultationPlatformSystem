package com.tcps.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcps.common.constant.CacheNames;
import com.tcps.common.core.domain.vo.request.RegisterRequest;
import com.tcps.common.enums.UserType;
import com.tcps.common.core.domain.entity.SysTenant;
import com.tcps.common.utils.StringUtils;
import com.tcps.system.domain.bo.SysTenantBo;
import com.tcps.common.core.domain.vo.SysTenantVo;
import com.tcps.system.mapper.SysTenantMapper;
import com.tcps.system.service.ISysTenantService;
import com.tcps.system.service.SysRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 租户Service接口实现类
 * @author Tao Guang
 */
@Service
@RequiredArgsConstructor
public class ISysTenantServiceImpl implements ISysTenantService {

    private final SysTenantMapper baseMapper;

    private final SysRegisterService sysRegisterService;

    /**
     * 基于租户ID查询租户并缓存
     * @param tenantId 租户ID
     * @return 租户信息视图对象
     */
    @Cacheable(value = CacheNames.SYS_TENANT, key = "#tenantId")
    @Override
    public SysTenantVo queryByTenantId(String tenantId) {
        return baseMapper.selectVoOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getTenantId, tenantId));
    }

    @Override
    public List<SysTenantVo> queryList(SysTenantBo sysTenantBo) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(sysTenantBo);
        return baseMapper.selectVoList(lqw);
    }

    @Transactional
    @Override
    public void insertTenant(SysTenantVo sysTenantVo) {
        SysTenant sysTenant=new SysTenant();
        BeanUtils.copyProperties(sysTenantVo,sysTenant);
        sysTenant.setTenantId(getTenantId());
        if(sysTenant.getAccountCount()==null){
            sysTenant.setAccountCount(5L);
        }
        baseMapper.insert(sysTenant);
        RegisterRequest request=new RegisterRequest();
        request.setUserType(UserType.HOSPITAL_USER.getUserType());
        request.setTenantId(sysTenant.getTenantId());
        request.setUsername("Sysadmin");
        request.setGrantType(1);
        request.setPassword("123456");
        sysRegisterService.register(request);
    }

    private LambdaQueryWrapper<SysTenant> buildQueryWrapper(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysTenant::getTenantId, bo.getTenantId());
        lqw.like(StringUtils.isNotBlank(bo.getContactUserName()), SysTenant::getContactUserName, bo.getContactUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), SysTenant::getContactPhone, bo.getContactPhone());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), SysTenant::getCompanyName, bo.getCompanyName());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseNumber()), SysTenant::getLicenseNumber, bo.getLicenseNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), SysTenant::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIntro()), SysTenant::getIntro, bo.getIntro());
        lqw.like(StringUtils.isNotBlank(bo.getDomain()), SysTenant::getDomain, bo.getDomain());
        lqw.eq(bo.getPackageId() != null, SysTenant::getPackageId, bo.getPackageId());
        lqw.eq(bo.getAccountCount() != null, SysTenant::getAccountCount, bo.getAccountCount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysTenant::getStatus, bo.getStatus());
        return lqw;
    }

    private String getTenantId(){
        String idStr = IdWorker.getIdStr();
        return idStr;
    }
}
