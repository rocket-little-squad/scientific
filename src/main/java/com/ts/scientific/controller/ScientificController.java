package com.ts.scientific.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.ScientificInfoConf;
import com.ts.scientific.service.StatisticsDetailService;
import com.ts.scientific.service.impl.ScientificInfoConfServiceImpl;
import com.ts.scientific.service.impl.ScientificInfoServiceImpl;
import com.ts.scientific.service.impl.StatisticsDetailServiceImpl;
import com.ts.scientific.util.PageUtil;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.ScientificInfoVo;
import com.ts.scientific.vo.StatisticsDetailVO;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

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

        @Autowired
        private ScientificInfoConfServiceImpl scientificInfoConfServiceImpl;

        @Autowired
        private ScientificInfoServiceImpl scientificInfoServiceImpl;
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

        /**
         * 科研项目添加
         */


        /**
         * 项目类别
         */
        @GetMapping("/getType")
        public Object getType(){
             return RepResult.repResult(0,"成功",scientificInfoServiceImpl.list());
        }

        /**
         * 计算条件查询
         */
        @GetMapping("/getCalculate")
        public Object getCalculate(Integer projectTypeId){
                return RepResult.repResult(0,"",scientificInfoConfServiceImpl.list(new QueryWrapper<ScientificInfoConf>().lambda()
                        .eq(ScientificInfoConf::getProjectTypeId,projectTypeId)));
        }


        //===========科研信息开始===============

        /**
         * 加载科研信息列表数据
         */
        @GetMapping("/loadAllScientificInfo")
        public Object loadAllScientificInfo(ScientificInfoVo scientificInfoVo) {
                return scientificInfoServiceImpl.queryAllScientificInfo(scientificInfoVo);
        }


       /* *//**
         * 添加科研信息
         *
         * @param scientificInfoVo
         * @return
         *//*
        @GetMapping("/addScientificInfo")
        public Object addScientificInfo(ScientificInfoVo scientificInfoVo) {
                return scientificInfoServiceImpl.insertSelective(scientificInfoVo);
        }

        *//**
         * 修改科研信息
         *
         * @param scientificInfoVo
         * @return
         *//*
        @GetMapping("/updateScientificInfo")
        public Object updateScientificInfo(ScientificInfoVo scientificInfoVo) {
                return scientificInfoServiceImpl.updateByPrimaryKeySelective(scientificInfoVo);
        }

        *//**
         * 删除科研信息
         *//*
        @GetMapping("/deleteScientificInfo")
        public Object deleteScientificInfo(String scientificInfoId) {
                return scientificInfoServiceImpl.deleteByPrimaryKey(scientificInfoId);
        }*/
        //===========科研信息结束===============


}
