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
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysOffice> selectOfficeList(SysOffice dept) {
        LambdaQueryWrapper<SysOffice> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysOffice::getDelFlag, "0")
            .eq(ObjectUtil.isNotNull(dept.getOfficeId()), SysOffice::getOfficeId, dept.getOfficeId())
            .eq(ObjectUtil.isNotNull(dept.getParentId()), SysOffice::getParentId, dept.getParentId())
            .like(StringUtils.isNotBlank(dept.getOfficeName()), SysOffice::getOfficeName, dept.getOfficeName())
            .eq(StringUtils.isNotBlank(dept.getStatus()), SysOffice::getStatus, dept.getStatus())
            .orderByAsc(SysOffice::getParentId)
            .orderByAsc(SysOffice::getOrderNum);
        return baseMapper.selectOfficeList(lqw);
    }

    /**
     * 查询部门树结构信息
     *
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    @Override
    public List<Tree<Long>> selectOfficeTreeList(SysOffice dept) {
        List<SysOffice> depts = this.selectOfficeList(dept);
        return buildOfficeTreeSelect(depts);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree<Long>> buildOfficeTreeSelect(List<SysOffice> depts) {
        if (CollUtil.isEmpty(depts)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(depts, (dept, tree) ->
            tree.setId(dept.getOfficeId())
                .setParentId(dept.getParentId())
                .setName(dept.getOfficeName())
                .setWeight(dept.getOrderNum()));
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
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public SysOffice selectOfficeById(Long deptId) {
        SysOffice dept = baseMapper.selectById(deptId);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysOffice parentOffice = baseMapper.selectOne(new LambdaQueryWrapper<SysOffice>()
            .select(SysOffice::getOfficeName).eq(SysOffice::getOfficeId, dept.getParentId()));
        dept.setParentName(ObjectUtil.isNotNull(parentOffice) ? parentOffice.getOfficeName() : null);
        return dept;
    }

    /**
     * 通过部门ID查询部门名称
     *
     * @param deptIds 部门ID串逗号分隔
     * @return 部门名称串逗号分隔
     */
    @Override
    public String selectOfficeNameByIds(String deptIds) {
        List<String> list = new ArrayList<>();
        for (Long id : StringUtils.splitTo(deptIds, Convert::toLong)) {
            SysOffice dept = SpringUtils.getAopProxy(this).selectOfficeById(id);
            if (ObjectUtil.isNotNull(dept)) {
                list.add(dept.getOfficeName());
            }
        }
        return String.join(StringUtils.SEPARATOR, list);
    }

    /**
     * 根据ID查询所有子部门数（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public long selectNormalChildrenOfficeById(Long deptId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getStatus, UserConstants.DEPT_NORMAL)
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOfficeId(Long deptId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getParentId, deptId));
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkOfficeExistUser(Long deptId) {
        return userMapper.exists(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getOfficeId, deptId));
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkOfficeNameUnique(SysOffice dept) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysOffice>()
            .eq(SysOffice::getOfficeName, dept.getOfficeName())
            .eq(SysOffice::getParentId, dept.getParentId())
            .ne(ObjectUtil.isNotNull(dept.getOfficeId()), SysOffice::getOfficeId, dept.getOfficeId()));
        return !exist;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkOfficeDataScope(Long deptId) {
        if (!LoginHelper.isAdmin()) {
            SysOffice dept = new SysOffice();
            dept.setOfficeId(deptId);
            List<SysOffice> depts = this.selectOfficeList(dept);
            if (CollUtil.isEmpty(depts)) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertOffice(SysOffice dept) {
        SysOffice info = baseMapper.selectById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + StringUtils.SEPARATOR + dept.getParentId());
        return baseMapper.insert(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#dept.deptId")
    @Override
    public int updateOffice(SysOffice dept) {
        SysOffice newParentOffice = baseMapper.selectById(dept.getParentId());
        SysOffice oldOffice = baseMapper.selectById(dept.getOfficeId());
        if (ObjectUtil.isNotNull(newParentOffice) && ObjectUtil.isNotNull(oldOffice)) {
            String newAncestors = newParentOffice.getAncestors() + StringUtils.SEPARATOR + newParentOffice.getOfficeId();
            String oldAncestors = oldOffice.getAncestors();
            dept.setAncestors(newAncestors);
            updateOfficeChildren(dept.getOfficeId(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
            && !StringUtils.equals(UserConstants.DEPT_NORMAL, dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentOfficeStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentOfficeStatusNormal(SysOffice dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        baseMapper.update(null, new LambdaUpdateWrapper<SysOffice>()
            .set(SysOffice::getStatus, UserConstants.DEPT_NORMAL)
            .in(SysOffice::getOfficeId, Arrays.asList(deptIds)));
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateOfficeChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysOffice> children = baseMapper.selectList(new LambdaQueryWrapper<SysOffice>()
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
        List<SysOffice> list = new ArrayList<>();
        for (SysOffice child : children) {
            SysOffice dept = new SysOffice();
            dept.setOfficeId(child.getOfficeId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(dept);
        }
        if (CollUtil.isNotEmpty(list)) {
            if (baseMapper.updateBatchById(list)) {
                list.forEach(dept -> CacheUtils.evict(CacheNames.SYS_DEPT, dept.getOfficeId()));
            }
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public int deleteOfficeById(Long deptId) {
        return baseMapper.deleteById(deptId);
    }

}
