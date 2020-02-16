package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.IUserService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author
 * @since 2020-02-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public Object queryAllUser(UserVo userVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(StringUtils.isBlank(userVo.getPhone()), User::getPhone, userVo.getPhone())
                .eq(StringUtils.isBlank(userVo.getUserName()), User::getUserName, userVo.getUserName());
        Page<User> userPage = userMapper.selectPage(new Page<>(userVo.getPage(), userVo.getLimit()), queryWrapper);
        List<User> users = userPage.getRecords();
        return RepResult.repResult(0, "查询成功", users, (int) userPage.getTotal());
    }

    @Override
    public Object insertSelective(User record) {

        if (null == record) {
            throw new BizException("添加数据为空");
        }
        if (1 != userMapper.insert(record)) {
            throw new BizException("添加用户数据失败");
        }
        return RepResult.repResult(0, "添加成功", new Object());
    }

    @Override
    public Object updateByPrimaryKeySelective(User record) {
        if (null == record) {
            throw new BizException("修改数据为空");
        }
        if (1 != userMapper.updateById(record)) {
            throw new BizException("修改用户数据失败");
        }
        return RepResult.repResult(0, "修改成功", new Object());
    }

    @Override
    public Object deleteByPrimaryKey(Integer id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if (1 != userMapper.deleteById(id)) {
            throw new BizException("删除用户数据失败");
        }
        return RepResult.repResult(0, "删除成功", new Object());
    }
}
