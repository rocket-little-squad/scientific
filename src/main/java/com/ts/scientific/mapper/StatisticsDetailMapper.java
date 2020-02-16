package com.ts.scientific.mapper;

import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.StatisticsDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.scientific.vo.StatisticsDetailVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface StatisticsDetailMapper extends BaseMapper<StatisticsDetail> {

    List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO);

}
