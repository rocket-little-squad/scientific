package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.Auth;
import com.ts.scientific.entity.Role;
import com.ts.scientific.mapper.AuthMapper;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.service.AuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.AuthVo;
import com.ts.scientific.vo.RoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Override
    public Object queryAllAuth(AuthVo authVo) {
        LambdaQueryWrapper<Auth> queryWrapper = new LambdaQueryWrapper<Auth>()
                .eq(StringUtils.isNotBlank(authVo.getAuthName()), Auth::getAuthName, authVo.getAuthName());
        Page<Auth> authPage = authMapper.selectPage(new Page<>(authVo.getPage(), authVo.getLimit()), queryWrapper);
        List<Auth> auths = authPage.getRecords();
        return RepResult.repResult(0, "查询成功", auths, (int) authPage.getTotal());
    }

    @Override
    public Object insertSelective(Auth record) {
        if (null == record) {
            throw new BizException("添加数据为空");
        }
        record.setCreateName(WebUtils.getCurrentUserName());
        record.setCreateTime(LocalDate.now());
        if (1 != authMapper.insert(record)) {
            throw new BizException("添加权限数据失败");
        }
        return RepResult.repResult(0, "添加成功", null);
    }

    @Override
    public Object deleteByPrimaryKey(Integer id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if (1 != authMapper.deleteById(id)) {
            throw new BizException("删除权限数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }

    @Override
    public Object updateByPrimaryKeySelective(AuthVo authVo) {
        if (null == authVo) {
            throw new BizException("修改数据为空");
        }
        if (1 != authMapper.updateById(authVo)) {
            throw new BizException("修改权限数据失败");
        }
        return RepResult.repResult(0, "修改成功", null);
    }
}
