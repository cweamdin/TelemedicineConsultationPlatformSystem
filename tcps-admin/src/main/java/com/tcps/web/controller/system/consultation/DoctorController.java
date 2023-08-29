package com.tcps.web.controller.system.consultation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.common.core.domain.R;
import com.tcps.consultation.domain.bo.DoctorBo;
import com.tcps.consultation.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author oy
 */
@RestController
@RequestMapping("/system/consultation/doctorController")
@Validated
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/pageQueryDoctorList")
    public R<List<DoctorBo>> pageQueryDoctorList(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestBody DoctorBo doctorBo){
        System.out.println(doctorBo.getName()+"====");
        Page<DoctorBo> doctorBoPage = new Page<>(page, pageSize);
        //调用服务层方法进行分页查询
        List<DoctorBo> doctorBos = doctorService.pageSelectDoctorList(doctorBoPage,doctorBo);
        System.out.println(doctorBos);
        return R.ok(doctorBos);
    }

    @PostMapping("/modifyDoctor")
    public R modifyDoctor(@Validated @RequestBody DoctorBo doctorBo){
        return R.ok(doctorService.update(doctorBo, null));
    }


    @GetMapping("/queryDoctorId/{usernameId}")
    public R queryDoctorId(@PathVariable("usernameId") Integer usernameId) {
        return R.ok(doctorService.getById(usernameId));
    }

}
