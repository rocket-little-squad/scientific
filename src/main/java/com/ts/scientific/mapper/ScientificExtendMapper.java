package com.ts.scientific.mapper;

import com.ts.scientific.dto.DeptStatisticsDto;
import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.ScientificInfo;
import com.ts.scientific.vo.ScientificInfoVo;
import com.ts.scientific.vo.StatisticsDetailVO;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ScientificExtendMapper {

    List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO);

    int count(ScientificInfoVo scientificInfoVo);

    List<ScientificInfo> queryAllScientificInfo(ScientificInfoVo scientificInfoVo);

    List<DeptStatisticsDto> getDeptStatistics(Map<String,Object> map);
}
