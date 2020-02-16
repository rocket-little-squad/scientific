package com.ts.scientific.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 科研信息配置表 前端控制器
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/scientific")
public class ScientificController {

        @GetMapping("/t1")
        public Object t1(){
            throw new BizException("错误");
        }

}
