package com.ts.scientific.controller;

import com.ts.scientific.service.impl.AuthServiceImpl;
import com.ts.scientific.service.impl.RoleServiceImpl;
import com.ts.scientific.service.impl.UserServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.AuthVo;
import com.ts.scientific.vo.RoleVo;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private AuthServiceImpl authServiceImpl;


    //==================用户开始=========================

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
    @GetMapping("/addUser")
    public Object addUser(UserVo userVo) {
        return userServiceImpl.insertSelective(userVo);
    }

    /**
     * 修改用户
     *
     * @param userVo
     * @return
     */
    @GetMapping("/updateUser")
    public Object updateUser(UserVo userVo) {
        return userServiceImpl.updateByPrimaryKeySelective(userVo);
    }

    /**
     * 删除用户
     */
    @GetMapping("/deleteUser")
    public Object deleteUser(String userId) {
        return userServiceImpl.deleteByPrimaryKey(userId);
    }

    //=================用户结束==================


    //==================角色开始==================

    /**
     * 加载角色列表数据
     */
    @RequestMapping("loadAllRole")
    public Object loadAllRole(RoleVo roleVo) {
        return roleServiceImpl.queryAllRole(roleVo);
    }

    /**
     * 添加角色
     */
    @RequestMapping("addRole")
    public Object addRole(RoleVo RoleVo) {
        return roleServiceImpl.insertSelective(RoleVo);
    }

    /**
     * 删除角色
     */
    @RequestMapping("deleteRole")
    public Object deleteRole(Integer id) {
        return roleServiceImpl.deleteByPrimaryKey(id);
    }

    /**
     * 修改角色
     */
    @RequestMapping("updateRole")
    public Object updateRole(RoleVo roleVo) {
        return roleServiceImpl.updateByPrimaryKeySelective(roleVo);
    }


    //==================角色结束==================



    //==================权限开始===================



    /**
     * 加载权限列表数据
     */
    @RequestMapping("loadAllAuth")
    public Object loadAllAuth(AuthVo authVo) {
        return authServiceImpl.queryAllAuth(authVo);
    }

    /**
     * 添加权限
     */
    @RequestMapping("addAuth")
    public Object addAuth(AuthVo AuthVo) {

        return authServiceImpl.insertSelective(AuthVo);
    }

    /**
     * 删除权限
     */
    @RequestMapping("deleteAuth")
    public Object deleteAuth(Integer id) {
        return authServiceImpl.deleteByPrimaryKey(id);
    }

    /**
     * 修改权限
     */
    @RequestMapping("updateAuth")
    public Object updateAuth(AuthVo authVo) {
        return authServiceImpl.updateByPrimaryKeySelective(authVo);
    }




    //==================权限结束===================


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
