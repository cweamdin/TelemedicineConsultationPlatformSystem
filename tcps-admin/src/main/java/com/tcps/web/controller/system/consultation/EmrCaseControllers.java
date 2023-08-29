package com.tcps.web.controller.system.consultation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.common.core.domain.R;
import com.tcps.consultation.domain.EmrCase;
import com.tcps.consultation.service.IEmrCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李太白
 * @createTime: 2023/08/29 14:30
 * @description: 病历api
 */
@RestController
@RequestMapping("/system/consultation/emr_case")
public class EmrCaseControllers {
    @Autowired
    private IEmrCaseService iEmrCaseService;

    @GetMapping("/notdiagnosed")
    public R<Page<EmrCase>> notDiagnosed(Long index,Long pageSize){
        Page<EmrCase> page=iEmrCaseService.notBatchDiagnosed(index,pageSize);
        return R.ok(page);
    }

}
