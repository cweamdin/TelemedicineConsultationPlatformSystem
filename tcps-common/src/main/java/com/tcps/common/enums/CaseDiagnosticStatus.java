package com.tcps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李太白
 * @createTime: 2023/08/29 15:11
 * @description: 病历诊断状态enum
 */
@Getter
@AllArgsConstructor
public enum CaseDiagnosticStatus {
    noT_Filed('0',"未提交"),
    No_Diagnostics('1',"待诊断"),
    OK_Diagnostics('2',"已诊断"),
    Out_Diagnostics('3',"被退回"),
    OK_Revocation('4',"已撤回")
    ;
    final char code;
    final String DiagnosticMessage;
}
