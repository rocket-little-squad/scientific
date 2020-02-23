package com.ts.scientific.dto;

import com.ts.scientific.entity.ScientificInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class ScientificInfoDto extends ScientificInfo implements Serializable {

    /**
     * 计算标准
     */
    private String calculateScore;

    /**
     * 计算条件
     */
    private String calculateCondition;

}
