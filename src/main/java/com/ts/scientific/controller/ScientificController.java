package com.ts.scientific.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.IStatisticsDetailService;
import com.ts.scientific.vo.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/scientific")
public class ScientificController {

        @Autowired
        private IStatisticsDetailService iStatisticsDetailService;

        /**
         * 科研绩效查询
         */
        @GetMapping("/getScientificDetail")
        public Object getScientificDetail(StatisticsDetailVO statisticsDetailVO){
            return iStatisticsDetailService.getStatistics(statisticsDetailVO);
        }

}
