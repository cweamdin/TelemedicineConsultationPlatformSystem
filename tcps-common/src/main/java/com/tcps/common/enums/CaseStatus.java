package com.tcps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李太白
 * @createTime: 2023/08/28 14:14
 * @description: 分配类型状态enum
 */
@Getter
@AllArgsConstructor
public enum CaseStatus {

    Apply_Status(0,"申请状态"),
    Allocated_Status(1,"已分配状态"),
    Revocation_Status(2,"待撤回状态"),
    Out_Status(3,"被退回状态")
    ;


    final int caseStatus;
    final String  caseStatusMessage;
}
