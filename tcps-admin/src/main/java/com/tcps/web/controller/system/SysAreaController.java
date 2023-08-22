package com.tcps.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.tcps.common.core.controller.BaseController;
import com.tcps.common.core.domain.R;
import com.tcps.system.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * * 系统区域控制层
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysArea")
public class SysAreaController extends BaseController {

    private ISysAreaService service;

    /**
     * * 查询区域所有信息
     * @return
     */
//    @SaCheckPermission("manage:area:list")
    @GetMapping("/list")
    public R<List> findAllSysArea(){
        return R.ok(service.list());
    }
}
