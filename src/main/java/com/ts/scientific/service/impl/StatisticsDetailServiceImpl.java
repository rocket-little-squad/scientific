package com.ts.scientific.service.impl;

import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.StatisticsDetail;
import com.ts.scientific.mapper.ScientificExtendMapper;
import com.ts.scientific.mapper.StatisticsDetailMapper;
import com.ts.scientific.service.StatisticsDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.vo.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 统计表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class StatisticsDetailServiceImpl extends ServiceImpl<StatisticsDetailMapper, StatisticsDetail> implements StatisticsDetailService {

    @Autowired
    private ScientificExtendMapper scientificExtendMapper;

    @Override
    public List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO) {
        return scientificExtendMapper.getStatistics(statisticsDetailVO);
    }
}
