package com.tcps.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tcps.common.constant.CacheNames;
import com.tcps.common.constant.UserConstants;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.core.domain.entity.SysRole;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.core.service.OfficeService;
import com.tcps.common.exception.ServiceException;
import com.tcps.common.helper.DataBaseHelper;
import com.tcps.common.helper.LoginHelper;
import com.tcps.common.utils.StringUtils;
import com.tcps.common.utils.TreeBuildUtils;
import com.tcps.common.utils.redis.CacheUtils;
import com.tcps.common.utils.spring.SpringUtils;
import com.tcps.system.mapper.SysOfficeMapper;
import com.tcps.system.mapper.SysRoleMapper;
import com.tcps.system.mapper.SysUserMapper;
import com.tcps.system.service.ISysOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 部门管理 服务实现
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysOfficeServiceImpl implements ISysOfficeService,OfficeService {

    private final SysOfficeMapper baseMapper;
    private final SysRoleMapper roleMapper;
    private final SysUserMapper userMapper;

    /**
     * 查询部门管理数据
     *
     * @param office 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysOffice> selectOfficeList(SysOffice office) {
        LambdaQueryWrapper<SysOffice> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysOffice::getDelFlag, "0")
            .eq(ObjectUtil.isNotNull(office.getOfficeId()), SysOffice::getOfficeId, office.getOfficeId())
            .eq(ObjectUtil.isNotNull(office.getParentId()), SysOffice::getParentId, office.getParentId())
            .like(StringUtils.isNotBlank(office.getOfficeName()), SysOffice::getOfficeName, office.getOfficeName())
            .eq(StringUtils.isNotBlank(office.getStatus()), SysOffice::getStatus, office.getStatus())
            .orderByAsc(SysOffice::getParentId)
            .orderByAsc(SysOffice::getOrderNum);
        return baseMapper.selectOfficeList(lqw);
    }

    /**
     * 查询部门树结构信息
     *
     * @param office 部门信息
     * @return 部门树信息集合
     */
    @Override
    public List<Tree<Long>> selectOfficeTreeList(SysOffice office) {
        List<SysOffice> offices = this.selectOfficeList(office);
        return buildOfficeTreeSelect(offices);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param offices 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree<Long>> buildOfficeTreeSelect(List<SysOffice> offices) {
        if (CollUtil.isEmpty(offices)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(offices, (office, tree) ->
            tree.setId(office.getOfficeId())
                .setParentId(office.getParentId())
                .setName(office.getOfficeName())
                .setWeight(office.getOrderNum()));
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectOfficeListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectById(roleId);
        return baseMapper.selectOfficeListByRoleId(roleId, role.getOfficeCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param officeId 部门ID
     * @return 部门信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#officeId")
    @Override
    public SysOffice selectOfficeById(Long officeId) {
        SysOffice office = baseMapper.selectById(officeId);
        if (ObjectUtil.isNull(office)) {
            return null;
        }
        SysOffice parentOffice = baseMapper.selectOne(new LambdaQueryWrapper<SysOffice>()
            .select(SysOffice::getOfficeName).eq(SysOffice::getOfficeId, office.getParentId()));
        office.setParentName(ObjectUtil.isNotNull(parentOffice) ? parentOffice.getOfficeName() : null);
        return office;
    }

    /**
     * 通过部门ID查询部门名称
     *
     * @param officeIds 部门ID串逗号分隔
     * @return 部门名称串逗号分隔
     */
    @Override
    public String selectOfficeNameByIds(String officeIds) {
        List<String> list = new ArrayList<>();
        for (Long id : StringUtils.splitTo(officeIds, Convert::toLong)) {
            SysOffice office = SpringUtils.getAopProxy(this).selectOfficeById(id);
            if (ObjectUtil.isNotNull(office)) {
                list.add(office.getOfficeName());
            }
        }
        return String.join(StringUtils.SEPARATOR, list);
    }

    /**
     * 根据ID查询所有子部门数（正常状态）
     *
     * @param officeId 部门ID
     * @return 子部门数
     */
    @Override
    public long selectNormalChildrenOfficeById(Long officeId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getStatus, UserConstants.DEPT_NORMAL)
            .apply(DataBaseHelper.findInSet(officeId, "ancestors")));
    }

    /**
     * 是否存在子节点
     *
     * @param officeId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOfficeId(Long officeId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getParentId, officeId));
    }

    /**
     * 查询部门是否存在用户
     *
     * @param officeId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkOfficeExistUser(Long officeId) {
        return userMapper.exists(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getOfficeId, officeId));
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param office 部门信息
     * @return 结果
     */
    @Override
    public boolean checkOfficeNameUnique(SysOffice office) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getOfficeName, office.getOfficeName())
            .eq(SysOffice::getParentId, office.getParentId())
            .ne(ObjectUtil.isNotNull(office.getOfficeId()), SysOffice::getOfficeId, office.getOfficeId()));
        return !exist;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param officeId 部门id
     */
    @Override
    public void checkOfficeDataScope(Long officeId) {
        if (!LoginHelper.isAdmin()) {
            SysOffice office = new SysOffice();
            office.setOfficeId(officeId);
            List<SysOffice> offices = this.selectOfficeList(office);
            if (CollUtil.isEmpty(offices)) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param office 部门信息
     * @return 结果
     */
    @Override
    public int insertOffice(SysOffice office) {
        SysOffice info = baseMapper.selectById(office.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        office.setAncestors(info.getAncestors() + StringUtils.SEPARATOR + office.getParentId());
        return baseMapper.insert(office);
    }

    /**
     * 修改保存部门信息
     *
     * @param office 部门信息
     * @return 结果
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#office.officeId")
    @Override
    public int updateOffice(SysOffice office) {
        SysOffice newParentOffice = baseMapper.selectById(office.getParentId());
        SysOffice oldOffice = baseMapper.selectById(office.getOfficeId());
        if (ObjectUtil.isNotNull(newParentOffice) && ObjectUtil.isNotNull(oldOffice)) {
            String newAncestors = newParentOffice.getAncestors() + StringUtils.SEPARATOR + newParentOffice.getOfficeId();
            String oldAncestors = oldOffice.getAncestors();
            office.setAncestors(newAncestors);
            updateOfficeChildren(office.getOfficeId(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(office);
        if (UserConstants.DEPT_NORMAL.equals(office.getStatus()) && StringUtils.isNotEmpty(office.getAncestors())
            && !StringUtils.equals(UserConstants.DEPT_NORMAL, office.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentOfficeStatusNormal(office);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param office 当前部门
     */
    private void updateParentOfficeStatusNormal(SysOffice office) {
        String ancestors = office.getAncestors();
        Long[] officeIds = Convert.toLongArray(ancestors);
        baseMapper.update(null, new LambdaUpdateWrapper<SysOffice>()
            .set(SysOffice::getStatus, UserConstants.DEPT_NORMAL)
            .in(SysOffice::getOfficeId, Arrays.asList(officeIds)));
    }

    /**
     * 修改子元素关系
     *
     * @param officeId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateOfficeChildren(Long officeId, String newAncestors, String oldAncestors) {
        List<SysOffice> children = baseMapper.selectList(new LambdaQueryWrapper<SysOffice>()
            .apply(DataBaseHelper.findInSet(officeId, "ancestors")));
        List<SysOffice> list = new ArrayList<>();
        for (SysOffice child : children) {
            SysOffice office = new SysOffice();
            office.setOfficeId(child.getOfficeId());
            office.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(office);
        }
        if (CollUtil.isNotEmpty(list)) {
            if (baseMapper.updateBatchById(list)) {
                list.forEach(office -> CacheUtils.evict(CacheNames.SYS_DEPT, office.getOfficeId()));
            }
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param officeId 部门ID
     * @return 结果
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#officeId")
    @Override
    public int deleteOfficeById(Long officeId) {
        return baseMapper.deleteById(officeId);
    }

}
