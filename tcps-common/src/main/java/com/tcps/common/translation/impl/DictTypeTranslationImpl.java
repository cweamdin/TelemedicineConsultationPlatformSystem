package com.tcps.common.translation.impl;

import com.tcps.common.annotation.TranslationType;
import com.tcps.common.constant.TransConstant;
import com.tcps.common.core.service.DictService;
import com.tcps.common.translation.TranslationInterface;
import com.tcps.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 字典翻译实现
 *
 * @author Tao Guang
 */
@Component
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, key.toString());
        }
        return null;
    }
}
