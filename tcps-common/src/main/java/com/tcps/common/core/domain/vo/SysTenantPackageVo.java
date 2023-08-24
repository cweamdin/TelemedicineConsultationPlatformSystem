package com.tcps.common.core.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.tcps.common.annotation.ExcelDictFormat;
import com.tcps.common.convert.ExcelDictConvert;
import com.tcps.common.core.domain.entity.SysTenantPackage;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户套餐视图对象 sys_tenant_package
 *
 * @author Michelle.Chung
 */
@Data
@ExcelIgnoreUnannotated
public class SysTenantPackageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户套餐id
     */
    private Long packageId;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 关联菜单id
     */
    private String menuIds;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;

    /**
     * 状态（0正常 1停用）
     */
    private String status;


}
