package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;



@EqualsAndHashCode(callSuper = true)
@Data
public class ScientificInfoVo extends ScientificInfo implements Serializable {

    //分页属性

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}