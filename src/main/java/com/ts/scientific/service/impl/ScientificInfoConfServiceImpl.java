package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.entity.ScientificInfoCentre;
import com.ts.scientific.entity.ScientificInfoConf;
import com.ts.scientific.mapper.ScientificInfoCentreMapper;
import com.ts.scientific.mapper.ScientificInfoConfMapper;
import com.ts.scientific.service.ScientificInfoConfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.RepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 科研信息配置表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class ScientificInfoConfServiceImpl extends ServiceImpl<ScientificInfoConfMapper, ScientificInfoConf> implements ScientificInfoConfService {

    @Resource
    private ScientificInfoCentreMapper scientificInfoCentreMapper;

    @Override
    public Object getCalculateIds(Integer projectTypeId) {
       List<ScientificInfoCentre>  centres = scientificInfoCentreMapper.selectList(new QueryWrapper<ScientificInfoCentre>().lambda()
                .eq(ScientificInfoCentre::getProjectTypeId,projectTypeId));
        List<ScientificInfoConf>  scientificInfoConfList = new ArrayList<>();
       if (!centres.isEmpty()){
            List<Integer> id = centres.stream().map(ScientificInfoCentre::getCalculateId).collect(Collectors.toList());
            scientificInfoConfList= this.list(new QueryWrapper<ScientificInfoConf>().lambda()
                    .in(ScientificInfoConf::getCalculateId,id));
       }
       return RepResult.repResult(0,"成功",scientificInfoConfList);
    }
}
