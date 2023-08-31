package com.tcps.common.translation.impl;

import com.tcps.common.annotation.TranslationType;
import com.tcps.common.constant.TransConstant;
import com.tcps.common.core.service.OfficeService;
import com.tcps.common.translation.TranslationInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 部门翻译实现
 *
 * @author Tao Guang
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class OfficeNameTranslationImpl implements TranslationInterface<String> {

    private final OfficeService deptService;

    @Override
    public String translation(Object key, String other) {
        return deptService.selectOfficeNameByIds(key.toString());
    }
}
