package com.tcps.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tcps.common.constant.CacheNames;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.core.domain.entity.SysTenant;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.utils.StringUtils;
import com.tcps.system.domain.bo.SysTenantBo;
import com.tcps.system.domain.vo.SysTenantVo;
import com.tcps.system.mapper.SysOfficeMapper;
import com.tcps.system.mapper.SysTenantMapper;
import com.tcps.system.mapper.SysUserMapper;
import com.tcps.system.service.ISysTenantService;
import com.tcps.system.service.SysRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 租户Service接口实现类
 * @author Tao Guang
 */
@Service
@RequiredArgsConstructor
public class ISysTenantServiceImpl implements ISysTenantService {

    private final SysTenantMapper baseMapper;

//    private final SysUserMapper userMapper;

    private final SysRegisterService sysRegisterService;

    private final SysOfficeMapper sysOfficeMapper;

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
        if(sysTenant.getAccountCount()==null){
            sysTenant.setAccountCount(5L);
        }
//        sysTenant.setTenantId(getTenantId());
//        baseMapper.insert(sysTenant);
//
//        SysUser sysUser =new SysUser();
//        sysUser.setTenantId(sysTenant.getTenantId());
//        sysUser.setCompanyId(sysTenant.getId());
//        sysUser.setUserName("Sysadmin");
//        sysUser.setName("超级管理员");
//        sysUser.setPassword(BCrypt.hashpw("123456"));
//        sysUser.setStatus("0");
//        sysUser.setUserType("医院");
//        sysTenant.setCreateBy("admin");
//        sysUser.setCreateTime(new Date());
//        sysUser.setUpdateBy("admin");
//        sysUser.setUpdateTime( new Date());
//        userMapper.insert(sysUser);

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
