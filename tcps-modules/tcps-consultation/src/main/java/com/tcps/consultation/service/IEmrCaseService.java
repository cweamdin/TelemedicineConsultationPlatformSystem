package com.tcps.consultation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.consultation.domain.EmrCase;

/**
 * @author: 李太白
 * @createTime: 2023/08/29 10:34
 * @description: 病历业务接口
 */
public interface IEmrCaseService {
    /**
     *  获取未诊断的病历
     * @param index
     * @param pageSize
     * @return
     */
    Page<EmrCase> notBatchDiagnosed(Long index, Long pageSize);
}
