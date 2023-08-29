package com.tcps.consultation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tcps.consultation.domain.bo.DoctorBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author oy
* @description 针对表【doctor(医生)】的数据库操作Mapper
* @createDate 2023-08-27 19:29:48
* @Entity com.tcps.consultation.domain.Doctor
*/
public interface DoctorMapper extends BaseMapper<DoctorBo> {
    List<DoctorBo> pageSelectDoctorList(@Param("page") Page<DoctorBo> page, @Param("doctorBo") DoctorBo doctorBo);
}




