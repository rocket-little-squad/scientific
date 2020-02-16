package com.ts.scientific.service;

import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.StatisticsDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.vo.StatisticsDetailVO;

import java.util.List;

/**
 * <p>
 * 统计表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface StatisticsDetailService extends IService<StatisticsDetail> {

    List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO);

}
