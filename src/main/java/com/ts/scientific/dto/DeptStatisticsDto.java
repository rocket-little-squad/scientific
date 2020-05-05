package com.ts.scientific.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DeptStatisticsDto implements Serializable {

    private String userName;

    private String title;

    private BigDecimal standardScore;

    private BigDecimal score;




}
