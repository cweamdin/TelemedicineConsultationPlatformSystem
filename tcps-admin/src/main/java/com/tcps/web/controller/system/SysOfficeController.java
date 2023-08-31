package com.tcps.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import com.tcps.common.annotation.Log;
import com.tcps.common.constant.UserConstants;
import com.tcps.common.core.controller.BaseController;
import com.tcps.common.core.domain.R;
import com.tcps.common.core.domain.entity.SysOffice;
import com.tcps.common.core.domain.vo.OfficeTreeSelectVo;
import com.tcps.common.core.domain.vo.SysUserVo;
import com.tcps.common.enums.BusinessType;
import com.tcps.common.utils.StringUtils;
import com.tcps.system.domain.vo.SysOfficeVo;
import com.tcps.system.service.ISysOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author Tao Guang
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/office")
public class SysOfficeController extends BaseController {

    private final ISysOfficeService officeService;

    /**
     * 获取部门列表
     */
    @SaCheckPermission("system:office:list")
    @GetMapping("/list")
    public R<List<SysOffice>> list(SysOffice office) {
        List<SysOffice> offices = officeService.selectOfficeList(office);
        return R.ok(offices);
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param officeId 部门ID
     */
    @SaCheckPermission("system:office:list")
    @GetMapping("/list/exclude/{officeId}")
    public R<List<SysOffice>> excludeChild(@PathVariable(value = "officeId", required = false) Long officeId) {
        List<SysOffice> offices = officeService.selectOfficeList(new SysOffice());
        offices.removeIf(d -> d.getOfficeId().equals(officeId)
            || StringUtils.splitList(d.getAncestors()).contains(Convert.toStr(officeId)));
        return R.ok(offices);
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param officeId 部门ID
     */
    @SaCheckPermission("system:office:query")
    @GetMapping(value = "/{officeId}")
    public R<SysOffice> getInfo(@PathVariable Long officeId) {
        officeService.checkOfficeDataScope(officeId);
        return R.ok(officeService.selectOfficeById(officeId));
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:office:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysOffice office) {
        if (!officeService.checkOfficeNameUnique(office)) {
            return R.fail("新增部门'" + office.getOfficeName() + "'失败，部门名称已存在");
        }
        return toAjax(officeService.insertOffice(office));
    }

    /**
     * 修改部门
     */
    @SaCheckPermission("system:office:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysOffice office) {
        Long officeId = office.getOfficeId();
        officeService.checkOfficeDataScope(officeId);
        if (!officeService.checkOfficeNameUnique(office)) {
            return R.fail("修改部门'" + office.getOfficeName() + "'失败，部门名称已存在");
        } else if (office.getParentId().equals(officeId)) {
            return R.fail("修改部门'" + office.getOfficeName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, office.getStatus())
            && officeService.selectNormalChildrenOfficeById(officeId) > 0) {
            return R.fail("该部门包含未停用的子部门！");
        }
        return toAjax(officeService.updateOffice(office));
    }

    /**
     * 删除部门
     *
     * @param officeId 部门ID
     */
    @SaCheckPermission("system:office:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{officeId}")
    public R<Void> remove(@PathVariable Long officeId) {
        if (officeService.hasChildByOfficeId(officeId)) {
            return R.warn("存在下级部门,不允许删除");
        }
        if (officeService.checkOfficeExistUser(officeId)) {
            return R.warn("部门存在用户,不允许删除");
        }
        officeService.checkOfficeDataScope(officeId);
        return toAjax(officeService.deleteOfficeById(officeId));
    }

    /**
     * 获取对应角色部门树列表
     *
     */
    @SaCheckPermission("system:office:list")
    @GetMapping(value = "/officeTree")
    public R<OfficeTreeSelectVo> officeTreeselect() {
        List<SysOffice> offices = officeService.selectOfficeList(new SysOffice());
        OfficeTreeSelectVo officeTreeSelectVo = new OfficeTreeSelectVo();
        officeTreeSelectVo.setOffcies(officeService.buildOfficeTreeSelect(offices));
        return R.ok(officeTreeSelectVo);
    }
}
