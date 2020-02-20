package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.Role;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.RoleVo;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Object queryAllRole(RoleVo roleVo) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .eq(StringUtils.isNotBlank(roleVo.getRoleName()), Role::getRoleName, roleVo.getRoleName());
        Page<Role> rolePage = roleMapper.selectPage(new Page<>(roleVo.getPage(), roleVo.getLimit()), queryWrapper);
        List<Role> roles = rolePage.getRecords();
        return RepResult.repResult(0, "查询成功", roles, (int) rolePage.getTotal());
    }

    @Override
    public Object insertSelective(Role record) {
        if (null == record) {
            throw new BizException("添加数据为空");
        }
        record.setCreateName(WebUtils.getCurrentUserName());
        record.setCreateTime(LocalDate.now());
        if (1 != roleMapper.insert(record)) {
            throw new BizException("添加角色数据失败");
        }
        return RepResult.repResult(0, "添加成功", null);
    }

    @Override
    public Object deleteByPrimaryKey(Integer id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if (1 != roleMapper.deleteById(id)) {
            throw new BizException("删除角色数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }

    @Override
    public Object updateByPrimaryKeySelective(RoleVo roleVo) {
        if (null == roleVo) {
            throw new BizException("修改数据为空");
        }
        if (1 != roleMapper.updateById(roleVo)) {
            throw new BizException("修改角色数据失败");
        }
        return RepResult.repResult(0, "修改成功", null);
    }
}
