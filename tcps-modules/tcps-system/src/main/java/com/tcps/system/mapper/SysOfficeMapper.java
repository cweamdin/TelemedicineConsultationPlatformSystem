package com.tcps.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tcps.common.annotation.DataColumn;
import com.tcps.common.annotation.DataPermission;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.core.mapper.BaseMapperPlus;
import com.tcps.system.domain.vo.SysOfficeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构管理 数据层
 *
 * @author Tao Guang
 */
public interface SysOfficeMapper extends BaseMapperPlus<SysOfficeMapper, SysOffice, SysOfficeVo> {

    /**
     * 查询机构管理数据
     *
     * @param queryWrapper 查询条件
     * @return 机构信息集合
     */
    @DataPermission({
        @DataColumn(key = "officeName", value = "office_id")
    })
    List<SysOffice> selectOfficeList(@Param(Constants.WRAPPER) Wrapper<SysOffice> queryWrapper);

    /**
     * 根据角色ID查询机构树信息
     *
     * @param roleId            角色ID
     * @param officeCheckStrictly 机构树选择项是否关联显示
     * @return 选中机构列表
     */
    List<Long> selectOfficeListByRoleId(@Param("roleId") Long roleId, @Param("officeCheckStrictly") boolean officeCheckStrictly);

}
