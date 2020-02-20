package com.ts.scientific.vo;

import com.ts.scientific.entity.Auth;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthVo extends Auth implements Serializable {

    //分页属性

    private Integer page = 1;
    private Integer limit = 10;

}
