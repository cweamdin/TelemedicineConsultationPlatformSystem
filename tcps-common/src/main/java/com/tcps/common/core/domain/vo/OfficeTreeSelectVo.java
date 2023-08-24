package com.tcps.common.core.domain.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * 机构树VO
 * @author Tao Guang
 */
@Data
public class OfficeTreeSelectVo {

    /**
     * 下拉树结构列表
     */
    private List<Tree<Long>> offcies;

}
