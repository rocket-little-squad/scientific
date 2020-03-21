package com.ts.scientific.vo;


import com.ts.scientific.entity.StatisticsDetail;
import com.ts.scientific.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProDetileVo extends StatisticsDetail implements Serializable {

    //分页属性

    private Integer page = 1;
    private Integer limit = 10;
}
