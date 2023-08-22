package com.tcps.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcps.system.domain.SysArea;
import com.tcps.system.mapper.SysAreaMapper;
import com.tcps.system.service.ISysAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * * 区域管理接口实现类
 */
@RequiredArgsConstructor
@Service
public class ISysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {
}
