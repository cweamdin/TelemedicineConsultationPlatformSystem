package com.tcps.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcps.common.utils.TreeBuildUtils;
import com.tcps.system.domain.SysArea;
import com.tcps.system.mapper.SysAreaMapper;
import com.tcps.system.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * * 区域管理接口实现类
 */
@RequiredArgsConstructor
@Service
public class ISysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Override
    public List<Tree<Long>> selectTreeList() {
        List<SysArea> list = this.list();
        return buildTreeList(list);
    }

    /**
     * 生成树形结构
     * @param areas 区域列表
     * @return 树形结构
     */
    private List<Tree<Long>> buildTreeList(List<SysArea> areas) {
        if (CollUtil.isEmpty(areas)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(areas, (area, tree) ->
            tree.setId(area.getAreaId())
                .setParentId(area.getParentId())
                .setName(area.getAreaName())
                .setWeight(area.getSort()));
    }
}
