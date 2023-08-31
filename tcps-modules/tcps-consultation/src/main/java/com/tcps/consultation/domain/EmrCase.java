package com.tcps.consultation.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author  李太白
 * @Date 2023-08-29
 */
@TableName ("emr_case" )
@Data
public class EmrCase  implements Serializable {
	private static final long serialVersionUID =  3398577107976123118L;

	/**
	 * 病理编号
	 */
   	@TableId(value = "id" )
	private String id;

	/**
	 * 病理类型（1 常规 2 细胞 3冰冻）
	 */
   	@TableField(value = "case_type" )
	private String caseType;

	/**
	 * 病理号
	 */
   	@TableField(value = "no" )
	private String no;

	/**
	 * 病人姓名
	 */
   	@TableField(value = "name" )
	private String name;

	/**
	 * 送检时间
	 */
   	@TableField(value = "send_time" )
	private Date sendTime;

	/**
	 * 性别（0 男，1 女）
	 */
   	@TableField(value = "sex" )
	private String sex;

	/**
	 * 年龄
	 */
   	@TableField(value = "age" )
	private Long age;

	/**
	 * 职业
	 */
   	@TableField(value = "job" )
	private String job;

	/**
	 * 年龄单位（0 岁，1月，2天）
	 */
   	@TableField(value = "age_unit" )
	private String ageUnit;

	/**
	 * 结婚状态（1 已婚 2 未婚 3 离婚）
	 */
   	@TableField(value = "marital_status" )
	private String maritalStatus;

	/**
	 * 民族
	 */
   	@TableField(value = "nation" )
	private String nation;

	/**
	 * 送检医院
	 */
   	@TableField(value = "send_hospital" )
	private String sendHospital;

	/**
	 * 送检科室
	 */
   	@TableField(value = "send_depart" )
	private String sendDepart;

	/**
	 * 送检医生
	 */
   	@TableField(value = "send_doctor" )
	private String sendDoctor;

	/**
	 * 检查号
	 */
   	@TableField(value = "inspect_no" )
	private String inspectNo;

	/**
	 * 住院号
	 */
   	@TableField(value = "inpatient_no" )
	private String inpatientNo;

	/**
	 * 床号
	 */
   	@TableField(value = "bed_no" )
	private String bedNo;

	/**
	 * 病区号
	 */
   	@TableField(value = "area_no" )
	private String areaNo;

	/**
	 * 取材部位
	 */
   	@TableField(value = "specimen" )
	private String specimen;

	/**
	 * 临床资料（临床描述）
	 */
   	@TableField(value = "clinical_record" )
	private String clinicalRecord;

	/**
	 * 病史信息
	 */
   	@TableField(value = "medical_history" )
	private String medicalHistory;

	/**
	 * 手术所见
	 */
   	@TableField(value = "operation_descript" )
	private String operationDescript;

	/**
	 * 临床诊断
	 */
   	@TableField(value = "clinical_diagnosis" )
	private String clinicalDiagnosis;

	/**
	 * 大体所见
	 */
   	@TableField(value = "gross_descript" )
	private String grossDescript;

	/**
	 * 免疫组化
	 */
   	@TableField(value = "ihc" )
	private String ihc;

	/**
	 * 原病理诊断
	 */
   	@TableField(value = "first_diagnosis" )
	private String firstDiagnosis;

	/**
	 * 备注信息
	 */
   	@TableField(value = "remarks" )
	private String remarks;

	/**
	 * 状态 （ 0 显示 1 不能显示）
	 */
   	@TableField(value = "status" )
	private String status;

	/**
	 * 会诊专家id
	 */
   	@TableField(value = "expert_id" )
	private String expertId;

	/**
	 * 会诊专家名字
	 */
   	@TableField(value = "expert_name" )
	private String expertName;

	/**
	 * 诊断意见
	 */
   	@TableField(value = "expert_diagnosis" )
	private String expertDiagnosis;

	/**
	 * 报告完成时间
	 */
   	@TableField(value = "expert_time" )
	private Date expertTime;

	/**
	 * 创建人
	 */
   	@TableField(value = "create_by" )
	private String createBy;

	/**
	 * 创建时间
	 */
   	@TableField(value = "create_time" )
	private Date createTime;

	/**
	 * 更新人
	 */
   	@TableField(value = "update_by" )
	private String updateBy;

	/**
	 * 更新时间
	 */
   	@TableField(value = "update_time" )
	private Date updateTime;

	/**
	 * 删除标记
	 */
   	@TableField(value = "del_flag" )
	private String delFlag;

	/**
	 * 租户编号
	 */
   	@TableField(value = "tenant_id" )
	private String tenantId;

	/**
	 * 病例分配状态（0 申请分诊病例，1 已分诊病例，2 待撤回病例，3被退回病例）
	 */
   	@TableField(value = "case_status" )
	private String caseStatus;

	/**
	 * 诊断状态（0 未提交 1 待诊断   2 已诊断  3  被退回  4 已撤回）
	 */
   	@TableField(value = "diagnostic_status" )
	private String diagnosticStatus;

}
