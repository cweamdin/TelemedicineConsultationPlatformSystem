package com.tcps.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.tcps.common.core.domain.entity.SysOffice;

import java.util.List;

/**
 * 部门管理 服务层
 *
 * @author Lion Li
 */
public interface ISysOfficeService {
    /**
     * 查询部门管理数据
     *
     * @param office 部门信息
     * @return 部门信息集合
     */
    List<SysOffice> selectOfficeList(SysOffice office);

    /**
     * 查询部门树结构信息
     *
     * @param office 部门信息
     * @return 部门树信息集合
     */
    List<Tree<Long>> selectOfficeTreeList(SysOffice office);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param offices 部门列表
     * @return 下拉树结构列表
     */
    List<Tree<Long>> buildOfficeTreeSelect(List<SysOffice> offices);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    List<Long> selectOfficeListByRoleId(Long roleId);

    /**
     * 根据部门ID查询信息
     *
     * @param officeId 部门ID
     * @return 部门信息
     */
    SysOffice selectOfficeById(Long officeId);

    /**
     * 根据ID查询所有子部门数（正常状态）
     *
     * @param officeId 部门ID
     * @return 子部门数
     */
    long selectNormalChildrenOfficeById(Long officeId);

    /**
     * 是否存在部门子节点
     *
     * @param officeId 部门ID
     * @return 结果
     */
    boolean hasChildByOfficeId(Long officeId);

    /**
     * 查询部门是否存在用户
     *
     * @param officeId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkOfficeExistUser(Long officeId);

    /**
     * 校验部门名称是否唯一
     *
     * @param office 部门信息
     * @return 结果
     */
    boolean checkOfficeNameUnique(SysOffice office);

    /**
     * 校验部门是否有数据权限
     *
     * @param officeId 部门id
     */
    void checkOfficeDataScope(Long officeId);

    /**
     * 新增保存部门信息
     *
     * @param office 部门信息
     * @return 结果
     */
    int insertOffice(SysOffice office);

    /**
     * 修改保存部门信息
     *
     * @param office 部门信息
     * @return 结果
     */
    int updateOffice(SysOffice office);

    /**
     * 删除部门管理信息
     *
     * @param officeId 部门ID
     * @return 结果
     */
    int deleteOfficeById(Long officeId);
}
