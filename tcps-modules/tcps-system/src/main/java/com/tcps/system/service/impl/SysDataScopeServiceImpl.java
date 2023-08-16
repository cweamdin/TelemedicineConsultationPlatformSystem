package com.tcps.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.helper.DataBaseHelper;
import com.tcps.common.utils.StreamUtils;
import com.tcps.system.domain.SysRoleOffice;
import com.tcps.system.mapper.SysOfficeMapper;
import com.tcps.system.mapper.SysRoleOfficeMapper;
import com.tcps.system.service.ISysDataScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限 实现
 * <p>
 * 注意: 此Service内不允许调用标注`数据权限`注解的方法
 * 例如: deptMapper.selectList 此 selectList 方法标注了`数据权限`注解 会出现循环解析的问题
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service("sdss")
public class SysDataScopeServiceImpl implements ISysDataScopeService {

    private final SysRoleOfficeMapper roleOfficeMapper;
    private final SysOfficeMapper deptMapper;

    @Override
    public String getRoleCustom(Long roleId) {
        List<SysRoleOffice> list = roleOfficeMapper.selectList(
            new LambdaQueryWrapper<SysRoleOffice>()
                .select(SysRoleOffice::getOfficeId)
                .eq(SysRoleOffice::getRoleId, roleId));
        if (CollUtil.isNotEmpty(list)) {
            return StreamUtils.join(list, rd -> Convert.toStr(rd.getOfficeId()));
        }
        return null;
    }

    @Override
    public String getOfficeAndChild(Long deptId) {
        List<SysOffice> deptList = deptMapper.selectList(new LambdaQueryWrapper<SysOffice>()
            .select(SysOffice::getOfficeId)
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
        List<Long> ids = StreamUtils.toList(deptList, SysOffice::getOfficeId);
        ids.add(deptId);
        List<SysOffice> list = deptMapper.selectList(new LambdaQueryWrapper<SysOffice>()
            .select(SysOffice::getOfficeId)
            .in(SysOffice::getOfficeId, ids));
        if (CollUtil.isNotEmpty(list)) {
            return StreamUtils.join(list, d -> Convert.toStr(d.getOfficeId()));
        }
        return null;
    }

}
