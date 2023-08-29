package com.tcps.consultation.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签约医生VO
 */
@NoArgsConstructor
@Data
public class ContractedDoctorVo {
    private int id;
    private String name;
    private String phone;
    private String province;
    private String company;
    private String office;
    private String job;
    private String expert;
    private String onLine;
    private int dmcount;
    private String remarks;
    private int from;
    private int pageSize;
}
