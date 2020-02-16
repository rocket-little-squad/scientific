package com.ts.scientific.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticsDetailVO implements Serializable {

    private String userName;

    private String depName;

    private Integer dimension;

    private Integer currentPage;

    private Integer pageSize;
}
