package com.ts.scientific.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProDetileDto {


    private Integer statisticsDetailId;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 积分
     */
    private BigDecimal score;

    private Integer userId;

    /**
     * 项目id
     */
    private Integer proId;

    /**
     * 项目id
     */
    private String proNo;

    private String userName;
}
