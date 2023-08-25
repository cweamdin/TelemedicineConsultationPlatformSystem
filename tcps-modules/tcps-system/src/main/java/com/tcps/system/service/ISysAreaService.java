package com.tcps.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcps.system.domain.SysArea;

import java.util.List;

/**
 * * 区域业务层
 */
public interface ISysAreaService extends IService<SysArea> {
    /**
     * 获取区域列表
     * @return 区域列表树形结构
     */
    List<Tree<Long>> selectTreeList();
}
