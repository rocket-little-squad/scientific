package com.ts.scientific.controller;


import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.service.StatisticsDetailService;
import com.ts.scientific.service.impl.StatisticsDetailServiceImpl;
import com.ts.scientific.util.PageUtil;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author 
 * @since 2020-02-16
 */
@RestController
@CrossOrigin
@RequestMapping("/scientific")
public class ScientificController {

        @Autowired
        private StatisticsDetailServiceImpl statisticsDetailServiceImpl;

        /**
         * 科研绩效查询
         */
        @GetMapping("/getScientificDetail")
        public Object getScientificDetail(StatisticsDetailVO statisticsDetailVO){
                if (statisticsDetailVO.getCurrentPage() != null && statisticsDetailVO.getPageSize() != null) {
                        statisticsDetailVO.setCurrentPage((statisticsDetailVO.getCurrentPage() - 1) * statisticsDetailVO.getPageSize());
                        statisticsDetailVO.setPageSize(statisticsDetailVO.getPageSize());
                }
                List<StatisticsDetailDto> statisticsDetailDtos = statisticsDetailServiceImpl.getStatistics(statisticsDetailVO);
                statisticsDetailVO.setCurrentPage(null);
                statisticsDetailVO.setPageSize(null);
                int count = statisticsDetailServiceImpl.getStatistics(statisticsDetailVO).size();
                return RepResult.repResult(0, "成功", statisticsDetailDtos, count);
        }

}
