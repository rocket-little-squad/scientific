package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.config.BizException;
import com.ts.scientific.dto.UserDto;
import com.ts.scientific.entity.Role;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.UserService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author
 * @since 2020-02-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;


    @Override
    public Object login(String email, String password, HttpServletRequest request) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getEmail,email)
                .eq(User::getPassword,password));
        if (user != null){
            HttpSession session= request.getSession();
            session.setAttribute("user",user);
            return RepResult.repResult(0,"登录成功",user);
        }
        throw new BizException("检查账号或邮箱");
    }

    @Override
    public Object queryAllUser(UserVo userVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(StringUtils.isNotBlank(userVo.getPhone()), User::getPhone, userVo.getPhone())
                .eq(StringUtils.isNotBlank(userVo.getUserName()), User::getUserName, userVo.getUserName());
        IPage<User> userPage = userMapper.selectPage(new Page<>(userVo.getPage(),userVo.getLimit()), queryWrapper);
        List<UserDto> dtoList = userPage.getRecords().stream().map(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            Role role = roleMapper.selectById(user.getRoleId());
            userDto.setRoleName(role.getRoleName());
            return userDto;
        }).collect(Collectors.toList());
        return RepResult.repResult(0, "查询成功", dtoList, (int)userPage.getTotal());
    }

    @Override
    public Object insertSelective(User record) {

        if (null == record) {
            throw new BizException("添加数据为空");
        }
        record.setCreateName(WebUtils.getCurrentUserName());
        record.setPassword("123456");
        record.setCreateTime(LocalDate.now());
        if (1 != userMapper.insert(record)) {
            throw new BizException("添加用户数据失败");
        }
        return RepResult.repResult(0, "添加成功", null);
    }

    @Override
    public Object updateByPrimaryKeySelective(User record) {
        if (null == record) {
            throw new BizException("修改数据为空");
        }
        if (1 != userMapper.updateById(record)) {
            throw new BizException("修改用户数据失败");
        }
        return RepResult.repResult(0, "修改成功", null);
    }

    @Override
    public Object saveUserRole(UserVo userVo) {
        return null;
    }

    @Override
    public Object deleteByPrimaryKey(String id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        if (1 != userMapper.deleteById(id)) {
            throw new BizException("删除用户数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }
}
