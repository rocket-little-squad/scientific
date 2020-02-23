package com.ts.scientific.mapper;

import com.ts.scientific.dto.ScientificInfoDto;
import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.vo.ScientificInfoVo;
import com.ts.scientific.vo.StatisticsDetailVO;

import java.util.List;

/**
 *
 */
public interface ScientificExtendMapper {

    List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO);

    int count(ScientificInfoVo scientificInfoVo);

    List<ScientificInfoDto> queryAllScientificInfo(ScientificInfoVo scientificInfoVo);

}
