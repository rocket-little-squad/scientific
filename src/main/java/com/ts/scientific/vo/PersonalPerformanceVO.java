package com.ts.scientific.vo;

import com.ts.scientific.entity.StatisticsDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonalPerformanceVO extends StatisticsDetail implements Serializable {

    private Integer projectTypeId;

    private String typeName;

    private String proNo;

    private String principal;
}
