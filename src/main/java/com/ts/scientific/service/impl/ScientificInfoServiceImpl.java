package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.dto.ScientificInfoDto;
import com.ts.scientific.entity.ScientificInfo;
import com.ts.scientific.mapper.ScientificExtendMapper;
import com.ts.scientific.mapper.ScientificInfoMapper;
import com.ts.scientific.service.ScientificInfoService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.ScientificInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 科研信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class ScientificInfoServiceImpl extends ServiceImpl<ScientificInfoMapper, ScientificInfo> implements ScientificInfoService {


    @Resource
    private ScientificExtendMapper scientificExtendMapper;


    @Override
    public Object queryAllScientificInfo(ScientificInfoVo request) {

        List<ScientificInfoDto> scientificInfoDtos = scientificExtendMapper.queryAllScientificInfo(request);
        int count = scientificExtendMapper.count(request);
        return RepResult.repResult(0,"查询成功",scientificInfoDtos,count) ;
    }
}
