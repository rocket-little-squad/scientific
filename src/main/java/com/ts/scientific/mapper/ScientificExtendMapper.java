package com.ts.scientific.mapper;

import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.vo.StatisticsDetailVO;

import java.util.List;

/**
 *
 */
public interface ScientificExtendMapper {

    List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO);

}
