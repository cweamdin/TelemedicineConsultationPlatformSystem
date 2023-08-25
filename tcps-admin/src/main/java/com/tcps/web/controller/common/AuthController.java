package com.tcps.web.controller.common;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import com.tcps.common.core.domain.R;
import com.tcps.common.core.domain.entity.SysMenu;
import com.tcps.common.core.domain.entity.SysUser;
import com.tcps.common.core.domain.model.LoginUser;
import com.tcps.common.core.domain.vo.SysTenantVo;
import com.tcps.common.core.domain.vo.response.LoginTenantResponse;
import com.tcps.common.core.domain.vo.response.TenantResponseListVo;
import com.tcps.common.utils.MapstructUtils;
import com.tcps.common.utils.StringUtils;
import com.tcps.system.domain.bo.SysTenantBo;
import com.tcps.system.service.ISysTenantService;
import com.tcps.common.core.domain.vo.request.LoginRequest;
import com.tcps.common.core.domain.vo.response.LoginResponse;
import com.tcps.common.helper.LoginHelper;
import com.tcps.common.helper.TenantHelper;
import com.tcps.common.utils.StreamUtils;
import com.tcps.system.domain.vo.RouterVo;
import com.tcps.system.service.ISysMenuService;
import com.tcps.system.service.ISysUserService;
import com.tcps.web.service.factory.LoginGranterFactory;
import com.tcps.web.service.strategy.AbstractLoginGranterStrategy;
import com.tcps.web.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证
 * @author Tao Guang
 */
@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AbstractLoginGranterStrategy loginGranterStrategy;

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;
    private final ISysTenantService tenantService;


    /**
     * 登录方法
     * @param loginRequest 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public R<LoginResponse> Login(@Validated @RequestBody LoginRequest loginRequest) {

        // todo 多端实现登录

        // 校验租户
        loginService.checkTenant(loginRequest.getTenantId());
        // 登录逻辑
        AbstractLoginGranterStrategy strategy = LoginGranterFactory.getStrategyNoNull(loginRequest.getGrantType());
        return R.ok(strategy.login(loginRequest));
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", loginUser.getRolePermission());
        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }


    /**
     * 登录页面租户下拉框
     *
     * @return 租户列表
     */
    @GetMapping("/tenant/list")
    public R<LoginTenantResponse> tenantList(HttpServletRequest request) throws Exception {
        List<SysTenantVo> tenantList = tenantService.queryList(new SysTenantBo());
        List<TenantResponseListVo> voList = MapstructUtils.convert(tenantList, TenantResponseListVo.class);
        // 获取域名
        String host;
        String referer = request.getHeader("referer");
        if (StringUtils.isNotBlank(referer)) {
            // 这里从referer中取值是为了本地使用hosts添加虚拟域名，方便本地环境调试
            host = referer.split("//")[1].split("/")[0];
        } else {
            host = new URL(request.getRequestURL().toString()).getHost();
        }
        // 根据域名进行筛选
        List<TenantResponseListVo> list = StreamUtils.filter(voList, vo ->
            StringUtils.equals(vo.getDomain(), host));
        // 返回对象
        LoginTenantResponse vo = new LoginTenantResponse();
        vo.setVoList(CollUtil.isNotEmpty(list) ? list : voList);
        vo.setTenantEnabled(TenantHelper.isEnable());
        return R.ok(vo);
    }




}
