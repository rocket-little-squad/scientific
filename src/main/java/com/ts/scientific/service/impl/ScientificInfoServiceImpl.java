package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.config.BizException;
import com.ts.scientific.dto.ScientificInfoDto;
import com.ts.scientific.entity.ScientificInfo;
import com.ts.scientific.entity.ScientificInfoCentre;
import com.ts.scientific.entity.ScientificInfoConf;
import com.ts.scientific.mapper.ScientificExtendMapper;
import com.ts.scientific.mapper.ScientificInfoCentreMapper;
import com.ts.scientific.mapper.ScientificInfoConfMapper;
import com.ts.scientific.mapper.ScientificInfoMapper;
import com.ts.scientific.service.ScientificInfoService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.ScientificInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 科研信息表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class ScientificInfoServiceImpl extends ServiceImpl<ScientificInfoMapper, ScientificInfo> implements ScientificInfoService {


    @Resource
    private ScientificExtendMapper scientificExtendMapper;

    @Resource
    private ScientificInfoMapper scientificInfoMapper;

    @Resource
    private ScientificInfoConfMapper scientificInfoConfMapper;

    @Resource
    private ScientificInfoCentreMapper scientificInfoCentreMapper;


    @Override
    public Object queryAllScientificInfo(ScientificInfoVo request) {
        request.setPage( (request.getPage() - 1) * request.getLimit());
        List<ScientificInfo> scientificInfoDtos = scientificExtendMapper.queryAllScientificInfo(request);
        int count = scientificExtendMapper.count(request);
        return RepResult.repResult(0,"查询成功",scientificInfoDtos,count);
    }


    @Transactional
    public Object insertSelective(ScientificInfo request){
        try {
            String[] ruleStr = request.getRule().split(",");
            int sum = 0;
            for (String s : ruleStr) {
                sum += Integer.valueOf(s.replace("%", ""));
            }
            if (sum != 100) {
                throw new BizException("权重规则合起来要为1");
            }
        }catch (Exception e){
            throw new BizException("权重规则错误");
        }
        ScientificInfo scientificInfo = new ScientificInfo();
        BeanUtils.copyProperties(request,scientificInfo);
        scientificInfo.setCreateName(WebUtils.getCurrentUserName());
        scientificInfo.setCreateTime(LocalDate.now());
        int insert = scientificInfoMapper.insert(scientificInfo);
        return RepResult.repResult(0,"添加成功",null) ;
    }

    @Transactional
    public Object updateScientificInfo(ScientificInfo request){
        if(1!=scientificInfoMapper.updateById(request)){
            return RepResult.repResult(0,"修改失败",null) ;
        }
        return RepResult.repResult(0,"修改成功",null) ;
    }


    @Transactional
    public Object saveProjectConf(ScientificInfoVo request){
        Integer[] ids = request.getIds();
        for (Integer id : ids) {
            ScientificInfoCentre scientificInfoCentre = new ScientificInfoCentre();
            scientificInfoCentre.setCalculateId(id);
            scientificInfoCentre.setProjectTypeId(request.getProjectTypeId());
            scientificInfoCentreMapper.insert(scientificInfoCentre);
        }

        return RepResult.repResult(0,"分配成功",null) ;
    }

    @Transactional
    public Object deleteScientificInfo(String id){
        List<ScientificInfoCentre> project_type_id1 = scientificInfoCentreMapper.selectList(new QueryWrapper<ScientificInfoCentre>().eq("project_type_id", id));
        for (ScientificInfoCentre scientificInfoCentre : project_type_id1) {
            scientificInfoCentre.setDelFlag(1);
            int i = scientificInfoCentreMapper.updateById(scientificInfoCentre);
        }


        ScientificInfo scientificInfo = scientificInfoMapper.selectById(id);
        scientificInfo.setDelFlag(1);
        if(1!=scientificInfoMapper.updateById(scientificInfo)){
            return RepResult.repResult(0,"删除失败",null) ;
        }

        return RepResult.repResult(0,"删除成功",null) ;
    }


}
