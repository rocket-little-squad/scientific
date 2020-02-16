package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.RepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @Autowired
    private UserMapper userMapper;

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
}
