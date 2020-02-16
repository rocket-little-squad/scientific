package com.ts.scientific.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StatisticsDetailDto implements Serializable {

    private String userName;

    private String depName;

    private BigDecimal score;

}
