package com.ts.scientific.controller;

import com.ts.scientific.service.IUserService;
import com.ts.scientific.vo.UserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.service.UserService;
import com.ts.scientific.util.RepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.annotation.Resource;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ScientificUser")
public class ScientificUserController {

    @Resource
    private IUserService userService;

    /**
     * 加载用户列表数据
     */
    @GetMapping("/loadAllUser")
    public Object loadAllUser(UserVo userVo) {
        return userService.queryAllUser(userVo);
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/addUser")
    public Object addUser(UserVo userVo) {
        return userService.insertSelective(userVo);
    }

    /**
     * 修改用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/updateUser")
    public Object updateUser(UserVo userVo) {
        return userService.updateByPrimaryKeySelective(userVo);
    }

    /**
     * 删除用户
     */
    @PostMapping("/deleteUser")
    public Object deleteUser(UserVo userVo) {
        return userService.deleteByPrimaryKey(userVo.getUserId());
    }



    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Object login(String email, String password, HttpServletRequest request){
       return userService.login(email,password,request);
    }





}
