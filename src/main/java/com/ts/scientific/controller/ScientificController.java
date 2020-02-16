package com.ts.scientific.controller;


import com.ts.scientific.service.StatisticsDetailService;
import com.ts.scientific.service.impl.StatisticsDetailServiceImpl;
import com.ts.scientific.vo.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
            return statisticsDetailServiceImpl.getStatistics(statisticsDetailVO);
        }

}
