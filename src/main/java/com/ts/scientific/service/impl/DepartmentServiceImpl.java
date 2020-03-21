package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.Department;
import com.ts.scientific.entity.Title;
import com.ts.scientific.mapper.DepartmentMapper;
import com.ts.scientific.mapper.TitleMapper;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.DepartmentVo;
import com.ts.scientific.vo.TitleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentServiceImpl {

    @Resource
    private DepartmentMapper departmentMapper;


    public Object queryAllDepartment(DepartmentVo departmentVo) {
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<Department>()
                .eq(StringUtils.isNotBlank(departmentVo.getDepName()), Department::getDepName, departmentVo.getDepName());
        IPage<Department> departmentPage = departmentMapper.selectPage(new Page<>(departmentVo.getPage(),departmentVo.getLimit()), queryWrapper);
        return RepResult.repResult(0, "查询成功", departmentPage.getRecords(), (int)departmentPage.getTotal());
    }

    public Object insertSelective(Department record) {

        if (null == record) {
            throw new BizException("添加数据为空");
        }
        if (1 != departmentMapper.insert(record)) {
            throw new BizException("添加数据失败");
        }

        return RepResult.repResult(0, "添加成功", null);
    }

    public Object updateByPrimaryKeySelective(Department record) {
        if (null == record) {
            throw new BizException("修改数据为空");
        }
        if (1 != departmentMapper.updateById(record)) {
            throw new BizException("修改数据失败");
        }

        return RepResult.repResult(0, "修改成功", null);
    }


    public Object deleteByPrimaryKey(String id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if(1!=departmentMapper.deleteById(id)){
            throw new BizException("删除数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }
    
}
