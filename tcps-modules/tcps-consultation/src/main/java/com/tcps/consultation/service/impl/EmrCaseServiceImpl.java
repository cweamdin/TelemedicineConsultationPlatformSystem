package com.tcps.consultation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.consultation.domain.EmrCase;
import com.tcps.consultation.mapper.EmrCaseMapper;
import com.tcps.consultation.service.IEmrCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 李太白
 * @createTime: 2023/08/29 10:35
 * @description: 病历业务实现类
 */
@Service
public class EmrCaseServiceImpl implements IEmrCaseService {

    @Autowired
    private EmrCaseMapper emrCaseMapper;

    @Override
    public Page<EmrCase> notBatchDiagnosed(Long index, Long pageSize) {
        emrCaseMapper.selectNotBatchDiagnosed(new Page<EmrCase>(index,pageSize),)
        return null;
    }
}
