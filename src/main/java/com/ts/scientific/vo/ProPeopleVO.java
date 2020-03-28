package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProPeopleVO extends ScientificProPeople {

    private String userName;
    //删除标识
    private Integer flag = 0;
    //上报标识
    private Integer userFlag = 0;
    //审核标识
    private Integer auditFlag = 0;
    //审核标识
    private Integer seeFlag = 0;
    //项目状态
    private Integer proStatus;
}
