package com.ts.scientific.controller;

import com.ts.scientific.service.impl.UserServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ScientificUser")
public class ScientificUserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 加载用户列表数据
     */
    @GetMapping("/loadAllUser")
    public Object loadAllUser(UserVo userVo) {
        return userServiceImpl.queryAllUser(userVo);
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/addUser")
    public Object addUser(UserVo userVo) {
        return userServiceImpl.insertSelective(userVo);
    }

    /**
     * 修改用户
     *
     * @param userVo
     * @return
     */
    @PostMapping("/updateUser")
    public Object updateUser(UserVo userVo) {
        return userServiceImpl.updateByPrimaryKeySelective(userVo);
    }

    /**
     * 删除用户
     */
    @PostMapping("/deleteUser")
    public Object deleteUser(String userId) {
        return userServiceImpl.deleteByPrimaryKey(userId);
    }

    /**
     * 登录
     */
    @GetMapping("/login")
    public Object login(String email, String password, HttpServletRequest request){
       return userServiceImpl.login(email,password,request);
    }

    /**
     * 登出
     */
    @GetMapping("/loginOut")
    public Object loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return RepResult.repResult(0,"登出",null);
    }


}
