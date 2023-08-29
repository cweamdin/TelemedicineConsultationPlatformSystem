package com.tcps.consultation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcps.consultation.domain.bo.DoctorBo;
import com.tcps.consultation.mapper.DoctorMapper;
import com.tcps.consultation.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author oy
* @description 针对表【doctorBo(医生)】的数据库操作Service实现
* @createDate 2023-08-27 19:29:48
*/
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, DoctorBo>
    implements DoctorService{

    @Autowired(required = false)
    private DoctorMapper doctorMapper;

    @Override
    public List<DoctorBo> pageSelectDoctorList(Page<DoctorBo> page, DoctorBo doctorBo) {
        System.out.println(doctorBo+"=======service");
        List<DoctorBo> doctorBos = doctorMapper.pageSelectDoctorList(page,doctorBo);//获取查询结果列表
        long total = page.getTotal();// 获取总记录数
        System.out.println(doctorBos);
        return doctorBos;
    }
}




