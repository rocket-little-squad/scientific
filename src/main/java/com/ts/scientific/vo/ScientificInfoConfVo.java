package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificInfoConf;
import lombok.Data;

import java.io.Serializable;

@Data
public class ScientificInfoConfVo extends ScientificInfoConf implements Serializable {
    //分页属性

    private Integer page = 1;
    private Integer limit = 10;
}
