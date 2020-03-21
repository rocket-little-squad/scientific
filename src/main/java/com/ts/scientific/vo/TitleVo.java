package com.ts.scientific.vo;


import com.ts.scientific.entity.Title;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class TitleVo extends Title implements Serializable {

    //分页属性

    private Integer page = 1;
    private Integer limit = 10;
}
