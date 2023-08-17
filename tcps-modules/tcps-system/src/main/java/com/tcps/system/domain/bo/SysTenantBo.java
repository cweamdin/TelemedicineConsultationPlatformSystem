package com.tcps.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.tcps.common.core.domain.BaseEntity;
import com.tcps.common.core.domain.entity.SysTenant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户业务对象 sys_tenant
 *
 * @author Michelle.Chung
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenant.class, reverseConvertGenerate = false)
public class SysTenantBo extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 联系人
     */
    private String contactUserName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String licenseNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 域名
     */
    private String domain;

    /**
     * 企业简介
     */
    private String intro;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户套餐编号
     */
    private Long packageId;

    /**
     * 用户数量（-1不限制）
     */
    private Long accountCount;

    /**
     * 租户状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
