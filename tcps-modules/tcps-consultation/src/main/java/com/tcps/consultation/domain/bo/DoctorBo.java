package com.tcps.consultation.domain.bo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 医生实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("doctor")
@ExcelIgnoreUnannotated
public class DoctorBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "编号")
    private String id;

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "性别")
    private String sex;

    @ExcelProperty(value = "年龄")
    private Integer age;

    @ExcelProperty(value = "电话")
    private String phone;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "工作年限")
    @TableField("job_age")
    private Integer jobAge;

    @ExcelProperty(value = "省份")
    private String province;

    @ExcelProperty(value = "城市")
    private String city;

    @ExcelProperty(value = "区县")
    private String dist;

    @ExcelProperty(value = "医院")
    private String company;

    @ExcelProperty(value = "科室")
    private String office;

    @ExcelProperty(value = "职称")
    private String job;

    @ExcelProperty(value = "擅长")
    private String expert;

    @ExcelProperty(value = "自我介绍")
    private String introduce;

    @ExcelProperty(value = "账号")
    @TableField("user_name")
    private String userName;

    @ExcelProperty(value = "密码")
    @TableField("user_pwd")
    private String userPwd;

    @ExcelProperty(value = "科室")
    @TableField("depart_id")
    private String departId;

    @ExcelProperty(value = "用户类型")
    @TableField("user_type")
    private String userType;

    @ExcelProperty(value = "状态（0未审核,1已审核）")
    private String status;

    @ExcelProperty(value = "是否可接诊，默认为1，      1:是，0：否")
    @TableField("on_line")
    private String onLine;

    @ExcelProperty(value = "会诊价格")
    private BigDecimal price;

    @ExcelProperty(value = "会诊数")
    private Integer dmcount;

    @ExcelProperty(value = "推荐人编号")
    @TableField("recommend_id")
    private String recommendId;

    private Integer sort;

    @ExcelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ExcelProperty(value = "审核人")
    @TableField("audit_by")
    private String auditBy;

    @ExcelProperty(value = "审核时间")
    @TableField("audit_time")
    private LocalDateTime auditTime;

    @ExcelProperty(value = "备注信息")
    private String remarks;

    @ExcelProperty(value = "删除标记")
    @TableField("del_flag")
    private String delFlag;

    @ExcelProperty(value = "租户编号")
    @TableField("tenant_id")
    private String tenantId;
/*
    @ExcelProperty(value = "当前页")
    private int from;
    @ExcelProperty(value = "页面容量")
    private int pageSize;*/
}
