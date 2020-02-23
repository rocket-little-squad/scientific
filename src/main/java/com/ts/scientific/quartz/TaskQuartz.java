package com.ts.scientific.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.entity.*;
import com.ts.scientific.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class TaskQuartz {

    @Resource
    private ScientificProMapper scientificProMapper;

    @Resource
    private ScientificProPeopleMapper scientificProPeopleMapper;

    @Resource
    private ScientificProPeopleInfoMapper scientificProPeopleInfoMapper;

    @Resource
    private ScientificInfoMapper scientificInfoMapper;

    @Resource
    private StatisticsDetailMapper statisticsDetailMapper;

    @Scheduled(cron = "0/10 * * * * ?")
    public void task(){
      List<ScientificPro> scientificPros = scientificProMapper.selectList(new QueryWrapper<ScientificPro>().lambda()
        .eq(ScientificPro::getProStatus,3));
        for (ScientificPro scientificPro : scientificPros) {
            BigDecimal score = scientificPro.getScore();
            ScientificInfo scientificInfo =  scientificInfoMapper.selectOne(new QueryWrapper<ScientificInfo>().lambda()
                    .eq(ScientificInfo::getProjectTypeId,scientificPro.getProjectTypeId()));
            //该项目的参与人员
           List<ScientificProPeople>  peoples =scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda().
                    eq(ScientificProPeople::getProId,scientificPro.getProId())
                    .orderByAsc(ScientificProPeople::getRank));
            if (scientificInfo.getRuleTime() != null){
                //结束时间与现在时间
                Period period = Period.between(LocalDate.now(),scientificPro.getEndTime());
                //开始时间与结束时间
                Period  per =  Period.between(scientificPro.getStartTime(),scientificPro.getEndTime());
                if (scientificInfo.getRuleTime() == 1){
                   int year = period.getYears();
                   score  = scientificPro.getScore()
                           .divide(new BigDecimal(per.getYears()),2, RoundingMode.HALF_UP)
                           .multiply(new BigDecimal(year));
                }
                if (scientificInfo.getRuleTime() == 2){
                    int months = period.getMonths();
                    score  = scientificPro.getScore()
                            .divide(new BigDecimal(per.getMonths()),2, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal(months));
                }
                if (scientificInfo.getRuleTime() == 3){
                    int day = period.getMonths();
                    score  = scientificPro.getScore()
                            .divide(new BigDecimal(per.getDays()),2, RoundingMode.HALF_UP)
                            .multiply(new BigDecimal(day));
                }
               String[] rules =  scientificInfo.getRule().split(",");
                if (rules.length >= peoples.size()) {
                    for (int i = 0; i < rules.length; i++) {
                        BigDecimal rule = new BigDecimal(rules[i].replace("%", ""))
                                .divide(new BigDecimal(100));
                        ScientificProPeople  people=  peoples.get(i);
                        StatisticsDetail detail = new StatisticsDetail();
                        detail.setCreateTime(LocalDate.now());
                        detail.setScore(scientificPro.getScore().divide(rule));
                        detail.setUserId(people.getUserId());
                        statisticsDetailMapper.insert(detail);
                        if ( i == peoples.size()-1) {
                            break;
                        }
                    }
                }else {
                    for (int i = 0; i < peoples.size() ; i++) {
                        ScientificProPeople  people=  peoples.get(i);
                        StatisticsDetail detail = new StatisticsDetail();
                        detail.setCreateTime(LocalDate.now());
                        detail.setUserId(people.getUserId());
                        //BigDecimal rule = new BigDecimal(1);
                        if ( i < rules.length-1) {
                            BigDecimal r = new BigDecimal(rules[i].replace("%", ""))
                                    .divide(new BigDecimal(100));
                            //rule = rule.subtract(r);
                            detail.setScore(scientificPro.getScore().divide(r));
                        }else {
                            BigDecimal rule = new BigDecimal(rules[rules.length-1].replace("%", ""))
                                    .divide(new BigDecimal(100)).divide(new BigDecimal(peoples.size()-i+1));
                            detail.setScore(scientificPro.getScore().divide(rule));
                        }
                        statisticsDetailMapper.insert(detail);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        String [] a = {"1","2"};
        System.out.println(a.length);
        System.out.println("100%".replace("%",""));
    }

}
