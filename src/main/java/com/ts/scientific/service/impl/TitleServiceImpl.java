package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.Department;
import com.ts.scientific.entity.Role;
import com.ts.scientific.entity.Title;
import com.ts.scientific.mapper.TitleMapper;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.TitleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleServiceImpl {

    @Resource
    private TitleMapper titleMapper;


    public Object queryAllTitle(TitleVo titleVo) {
        LambdaQueryWrapper<Title> queryWrapper = new LambdaQueryWrapper<Title>()
                .eq(StringUtils.isNotBlank(titleVo.getTitleName()), Title::getTitleName, titleVo.getTitleName());
        IPage<Title> titlePage = titleMapper.selectPage(new Page<>(titleVo.getPage(),titleVo.getLimit()), queryWrapper);
        return RepResult.repResult(0, "查询成功", titlePage.getRecords(), (int)titlePage.getTotal());
    }

    public Object insertSelective(Title record) {

        if (null == record) {
            throw new BizException("添加数据为空");
        }
        if (1 != titleMapper.insert(record)) {
            throw new BizException("添加数据失败");
        }

        return RepResult.repResult(0, "添加成功", null);
    }

    public Object updateByPrimaryKeySelective(Title record) {
        if (null == record) {
            throw new BizException("修改数据为空");
        }
        if (1 != titleMapper.updateById(record)) {
            throw new BizException("修改数据失败");
        }

        return RepResult.repResult(0, "修改成功", null);
    }


    public Object deleteByPrimaryKey(String id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if(1!=titleMapper.deleteById(id)){
            throw new BizException("删除数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }
    
}
