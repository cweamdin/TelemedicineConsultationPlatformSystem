package com.tcps.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.tcps.common.core.domain.R;
import com.tcps.common.core.domain.vo.SysTenantVo;
import com.tcps.system.service.ISysTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *<p>李太白</p>
 * *时间2023/8/24
 */
@RestController
@RequestMapping("/system/systenant")
@RequiredArgsConstructor
public class SysTenantController {

    private final ISysTenantService iSysTenantService;

    @PostMapping("/createTenant")
    @SaCheckPermission("siteuser:site:list")
    public R<Void> createTenant(@RequestBody SysTenantVo sysTenantVo){
        iSysTenantService.insertTenant(sysTenantVo);
        return R.ok();
    }
}
