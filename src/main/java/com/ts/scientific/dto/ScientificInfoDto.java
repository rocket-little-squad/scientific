package com.ts.scientific.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ScientificInfoDto  implements Serializable {



    private Integer projectTypeId;

    private String projectTypeName;

    /**
     * 规则
     */
    private String rule;

    /**
     * 时间分配规则(1:年 2:月 3:日)
     */
    private Integer ruleTime;

    private LocalDate createTime;

    private String createName;


    /**
     * 计算标准
     */
    private String calculateScore;

    /**
     * 计算条件
     */
    private String calculateCondition;

}
