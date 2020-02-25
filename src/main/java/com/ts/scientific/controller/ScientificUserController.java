package com.ts.scientific.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.ts.scientific.entity.Auth;
import com.ts.scientific.entity.Centre;
import com.ts.scientific.entity.Role;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.AuthMapper;
import com.ts.scientific.mapper.CentreMapper;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.impl.AuthServiceImpl;
import com.ts.scientific.service.impl.RoleServiceImpl;
import com.ts.scientific.service.impl.UserServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.AuthVo;
import com.ts.scientific.vo.RoleVo;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    /**
     * 获得用户角色与权限
     * @return
     */
    @RequestMapping("getUserRoleAuth")
    public Object queryRoleAuth(){
        String currentUserName = WebUtils.getCurrentUserName();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", currentUserName));
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_id", user.getRoleId()));
        List<Centre> centres = centreMapper.selectList(new QueryWrapper<Centre>().eq("role_id", user.getRoleId()));
        Map<String,List<String>> roleAuths = new HashMap<String,List<String>>();
        List<String> auths = new ArrayList<>();
        for (Centre centre : centres) {
            Auth auth_id = authMapper.selectOne(new QueryWrapper<Auth>().eq("auth_id", centre.getAuthId()));
            auths.add(auth_id.getAuthCode());
        }
        roleAuths.put(role.getRoleId().toString(),auths);
        return RepResult.repResult(0,"查询成功",roleAuths);
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


}
