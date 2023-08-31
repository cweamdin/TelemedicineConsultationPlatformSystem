package com.tcps.common.core.service;

/**
 * 通用 部门服务
 *
 * @author Tao Guang
 */
public interface OfficeService {

    /**
     * 通过部门ID查询部门名称
     *
     * @param OoficeIds 部门ID串逗号分隔
     * @return 部门名称串逗号分隔
     */
    String selectOfficeNameByIds(String OoficeIds);

}
