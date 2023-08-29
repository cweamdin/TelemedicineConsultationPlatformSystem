package com.tcps.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.common.core.controller.BaseController;
import com.tcps.common.core.domain.R;
import com.tcps.system.domain.SysArea;
import com.tcps.system.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * * 系统区域控制层
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysArea")
public class SysAreaController extends BaseController {

    private final ISysAreaService service;

    /**
     * * 查询区域所有信息
     * @return
     */
//    @SaCheckPermission("manage:area:list")
    @GetMapping("/findAllSysArea")
    public R<List<Tree<Long>>> findAllSysArea(){
        return R.ok(service.selectTreeList());
    }

    /**
     * * 添加区域信息
     * @param sysArea
     * @return
     */
    @PostMapping("/addNewSysArea")
    public R addNewSysArea(@RequestBody SysArea sysArea){
        if (!sysArea.getCode().equals(sysArea.getCode())){
            if (sysArea.getAreaName().equals(sysArea.getAreaName())){
               return R.fail(20426,"添加区域"+sysArea.getAreaName()+"失败昵称已存在!!!");
            }
            if (sysArea.getCode().equals(sysArea.getCode())){
               return R.fail(401,"添加区域"+sysArea.getAreaName()+"失败区域编码相同!!!");
            }
        }
        return R.ok(service.save(sysArea));
    }

    /**
     * * 根据id修改
     * @param sysArea
     * @return
     */
    @PostMapping("/updateById")
    public R updateById(@RequestBody SysArea sysArea){
        if (!sysArea.getCode().equals(sysArea.getCode())){
            if (sysArea.getAreaName().equals(sysArea.getAreaName())){
               return R.fail(20426,"修改区域昵称"+sysArea.getAreaName()+"失败,昵称已存在!!!");
            }
            if (sysArea.getCode().equals(sysArea.getCode())){
                return R.fail(401,"修改区域编号"+sysArea.getAreaName()+"失败,区域编码相同!!!");
            }
        }
        return R.ok(service.updateById(sysArea));
    }

    /**
     * * 根据id查询
     * @param id
     * @return
     */
    @PostMapping("/selectById")
    public R<SysArea> selectById(String id){
       return R.ok(service.getById(id));
    }



}

