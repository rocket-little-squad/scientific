package com.ts.scientific.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.service.UserService;
import com.ts.scientific.util.RepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/ScientificUser")
public class ScientificUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Object login(String email, String password, HttpServletRequest request){
       return userService.login(email,password,request);
    }





}
