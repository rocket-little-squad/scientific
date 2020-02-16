package com.ts.scientific.service;

import com.ts.scientific.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface UserService extends IService<User> {

    Object login(String email, String password, HttpServletRequest request);

}
