package com.ts.scientific.vo;


import com.ts.scientific.entity.User;
import lombok.Data;

@Data
public class UserVo extends User {

    //分页属性
    private Integer page;
    private Integer limit;
}
