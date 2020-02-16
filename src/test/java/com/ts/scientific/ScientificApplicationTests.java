package com.ts.scientific;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScientificApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        userMapper.selectList(new QueryWrapper<User>());
    }

}
