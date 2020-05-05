package com.ts.scientific.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.AuthMapper;
import com.ts.scientific.mapper.CentreMapper;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.impl.*;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    private TitleServiceImpl titleService;

    @Resource
    private DepartmentServiceImpl departmentService;

    @Resource
    private AuthMapper authMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private CentreMapper centreMapper;


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


    /**
     * 加载角色管理里面的分配权限的权限列表并选中已拥有的权限
     */
    @RequestMapping("loadRoleAuth")
    public Object loadRoleAuth(String id) {
        return authServiceImpl.loadRoleAuth(id);
    }


    /**
     * 保存角色和权限之间的关系
     */
    @RequestMapping("saveRoleAuth")
    public Object saveRoleAuth(RoleVo roleVo) {
        return roleServiceImpl.saveRoleAuth(roleVo);
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



    //==================职称开始=========================

    /**
     * 加载职称列表数据
     */
    @RequestMapping("/loadAllTitle")
    public Object loadAllTitle(TitleVo titleVo) {
        return titleService.queryAllTitle(titleVo);
    }


    /**
     * 添加职称
     *
     * @param titleVo
     * @return
     */
    @RequestMapping("/addTitle")
    public Object addTitle(TitleVo titleVo) {
        return titleService.insertSelective(titleVo);
    }

    /**
     * 修改职称
     *
     * @param titleVo
     * @return
     */
    @RequestMapping("/updateTitle")
    public Object updateTitle(TitleVo titleVo) {
        return titleService.updateByPrimaryKeySelective(titleVo);
    }

    /**
     * 删除职称
     */
    @RequestMapping("/deleteTitle")
    public Object deleteTitle(String id) {
        return titleService.deleteByPrimaryKey(id);
    }

    //=================职称结束==================


    //==================部门开始=========================

    /**
     * 加载部门列表数据
     */
    @RequestMapping("/loadAllDepartment")
    public Object loadAllDepartment(DepartmentVo departmentVo) {
        return departmentService.queryAllDepartment(departmentVo);
    }


    /**
     * 添加部门
     *
     * @param departmentVo
     * @return
     */
    @RequestMapping("/addDepartment")
    public Object addDepartment(DepartmentVo departmentVo) {
        return departmentService.insertSelective(departmentVo);
    }

    /**
     * 修改部门
     *
     * @param departmentVo
     * @return
     */
    @RequestMapping("/updateDepartment")
    public Object updateDepartment(DepartmentVo departmentVo) {
        return departmentService.updateByPrimaryKeySelective(departmentVo);
    }

    /**
     * 删除部门
     */
    @RequestMapping("/deleteDepartment")
    public Object deleteDepartment(String id) {
        return departmentService.deleteByPrimaryKey(id);
    }

    //=================部门结束==================

    /**
     * 获得用户角色与权限
     * @return
     */
    @RequestMapping("getUserRoleAuth")
    public Object queryRoleAuth(){
        return RepResult.repResult(0,"",authServiceImpl.queryRoleAuth());
    }



    /**
     * 获得所有部门
     * @return
     */
    @RequestMapping("getDepartment")
    public Object getDepartment(){
        return RepResult.repResult(0,"",userServiceImpl.getDepartment());
    }

    /**
     * 获得所有职称
     * @return
     */
    @RequestMapping("getTitle")
    public Object getTitle(){
        return RepResult.repResult(0,"",userServiceImpl.getTitle());
    }


    /**
     * 获得所有用户
     * @return
     */
    @RequestMapping("getUser")
    public Object getUser(){
        return RepResult.repResult(0,"",userServiceImpl.getUser());
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("updatePassword")
    public Object updatePassword(String password){
        return userServiceImpl.updatePassword(password);
    }

    /**
     * 登录
     */
    @GetMapping("/login")
    public Object login(String email, String password, HttpServletRequest request) {
        return userServiceImpl.login(email, password, request);
    }

    /**
     * 登出
     */
    @GetMapping("/loginOut")
    public Object loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return RepResult.repResult(0, "登出", null);
    }

    @GetMapping("/updateStandardScore")
    public Object updateStandardScore(User user){
        userMapper.update(user,new QueryWrapper<User>().lambda()
                .eq(User::getUserId,user.getUserId()));
        return RepResult.repResult(0, "成功", null);
    }

}
