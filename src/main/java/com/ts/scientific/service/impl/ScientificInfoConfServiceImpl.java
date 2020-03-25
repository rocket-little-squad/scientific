package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.dto.ProDetileDto;
import com.ts.scientific.entity.*;
import com.ts.scientific.mapper.*;
import com.ts.scientific.service.ScientificInfoConfService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.ProDetileVo;
import com.ts.scientific.vo.ScientificInfoConfVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 科研信息配置表 服务实现类
 * </p>
 *
 * @author
 * @since 2020-02-16
 */
@Service
public class ScientificInfoConfServiceImpl extends ServiceImpl<ScientificInfoConfMapper, ScientificInfoConf> implements ScientificInfoConfService {

    @Resource
    private ScientificInfoCentreMapper scientificInfoCentreMapper;
    @Resource
    private ScientificInfoConfMapper scientificInfoConfMapper;
    @Resource
    private ScientificProMapper scientificProMapper;
    @Resource
    private StatisticsDetailMapper statisticsDetailMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Object getCalculateIds(Integer projectTypeId) {
       List<ScientificInfoCentre>  centres = scientificInfoCentreMapper.selectList(new QueryWrapper<ScientificInfoCentre>().lambda()
                .eq(ScientificInfoCentre::getProjectTypeId,projectTypeId)
                .eq(ScientificInfoCentre::getDelFlag, 0));
        List<ScientificInfoConf>  scientificInfoConfList = new ArrayList<>();
       if (!centres.isEmpty()){
            List<Integer> id = centres.stream().map(ScientificInfoCentre::getCalculateId).collect(Collectors.toList());
            scientificInfoConfList= this.list(new QueryWrapper<ScientificInfoConf>().lambda()
                    .in(ScientificInfoConf::getCalculateId,id));
       }
       return RepResult.repResult(0,"成功",scientificInfoConfList);
    }



    public Object queryAllScientificInfoConf(ScientificInfoConfVo request){
        LambdaQueryWrapper<ScientificInfoConf> queryWrapper = new LambdaQueryWrapper<ScientificInfoConf>()
                .eq(StringUtils.isNotBlank(request.getCalculateCondition()),ScientificInfoConf::getCalculateCondition,request.getCalculateCondition())
                .eq(request.getCalculateScore()!=null,ScientificInfoConf::getCalculateScore,request.getCalculateScore())
                .eq(ScientificInfoConf::getDelFlag,0);

        Page<ScientificInfoConf> scientificInfoConfPage = scientificInfoConfMapper.selectPage(new Page<ScientificInfoConf>(request.getPage(), request.getLimit()), queryWrapper);
        List<ScientificInfoConf> records = scientificInfoConfPage.getRecords();

        return RepResult.repResult(0,"查询成功",records,(int)scientificInfoConfPage.getTotal());
    }

    public Object insertSelective(ScientificInfoConf scientificInfoConf){
        scientificInfoConf.setCreateTime(LocalDate.now());
        scientificInfoConf.setCreateName(WebUtils.getCurrentUserName());
        if(1!=scientificInfoConfMapper.insert(scientificInfoConf)){
            return RepResult.repResult(1,"添加失败",null);
        }
        return RepResult.repResult(0,"添加成功",null);
    }

    public Object updateScientificInfoConf(ScientificInfoConf scientificInfoConf){
        if(1!=scientificInfoConfMapper.updateById(scientificInfoConf)){
            return RepResult.repResult(1,"修改失败",null);
        }
        return RepResult.repResult(0,"修改成功",null);
    }

    public Object deleteScientificInfoConf(String id){

        ScientificInfoConf scientificInfoConf = scientificInfoConfMapper.selectOne(new QueryWrapper<ScientificInfoConf>().eq("calculate_id", id));
        scientificInfoConf.setDelFlag(1);
        if (1!=scientificInfoConfMapper.updateById(scientificInfoConf)){
            return RepResult.repResult(1,"删除失败",null);
        }
        return RepResult.repResult(0,"删除成功",null);
    }
    public Object loadScientificInfoConf(String id){

        List<ScientificInfoConf> ScientificInfoConfs = scientificInfoConfMapper.selectList(new QueryWrapper<ScientificInfoConf>().eq("del_flag",0));

        List<ScientificInfoCentre> ScientificInfoConf = scientificInfoCentreMapper.selectList(new QueryWrapper<ScientificInfoCentre>().eq("project_type_id", id).eq("del_flag",0));
        List<Map<String,Object>> list = new ArrayList<>();
        for (ScientificInfoConf r1 : ScientificInfoConfs) {
            Boolean LAY_CHECKED=false;
            for (ScientificInfoCentre r2 : ScientificInfoConf) {
                if(r1.getCalculateId()==r2.getCalculateId()) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            Map<String,Object> map=new HashMap<>();
            map.put("calculateId", r1.getCalculateId());
            map.put("calculateScore", r1.getCalculateScore());
            map.put("calculateCondition", r1.getCalculateCondition());
            map.put("LAY_CHECKED", LAY_CHECKED);
            list.add(map);
        }
        return RepResult.repResult(0, "查询成功", list);
    }

    public Object loadDetile(ProDetileVo proDetileVo){
        LambdaQueryWrapper<StatisticsDetail> queryWrapper = new LambdaQueryWrapper<StatisticsDetail>()
                .eq(null!=proDetileVo.getUserId(),StatisticsDetail::getUserId,proDetileVo.getUserId());
        Page<StatisticsDetail> statisticsDetailPage = statisticsDetailMapper.selectPage(new Page<>(proDetileVo.getPage(), proDetileVo.getLimit()), queryWrapper);
        List<ProDetileDto> collect = statisticsDetailPage.getRecords().stream().map(detail -> {
            ProDetileDto proDetileDto = new ProDetileDto();
            BeanUtils.copyProperties(detail,proDetileDto);
            proDetileDto.setProNo(scientificProMapper.selectOne(new QueryWrapper<ScientificPro>().lambda()
                    .eq(ScientificPro::getProId,proDetileDto.getProId())).getProNo());
            User user = userMapper.selectById(detail.getUserId());
            proDetileDto.setUserName(user.getUserName());
            return proDetileDto;
        }).collect(Collectors.toList());
        return RepResult.repResult(0,"成功",collect,(int)statisticsDetailPage.getTotal());
    }

}
