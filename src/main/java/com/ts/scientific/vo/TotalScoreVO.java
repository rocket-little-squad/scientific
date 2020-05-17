package com.ts.scientific.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TotalScoreVO implements Serializable {

    private BigDecimal totalScore;

    private String depName;

    private String title;

}
