package com.tcps.consultation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcps.consultation.domain.bo.DoctorBo;

import java.util.List;

/**
* @author oy
* @description 针对表【doctor(医生)】的数据库操作Service
* @createDate 2023-08-27 19:29:48
*/
public interface DoctorService extends IService<DoctorBo> {
    List<DoctorBo> pageSelectDoctorList(Page<DoctorBo> page, DoctorBo doctorBo);
}
