package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProSeeVO implements Serializable {

    private String createName;

    private String proType;

    private LocalDate start;

    private LocalDate end;

    private String calculateName;

    private LocalDate createTime;

    private String  proNo;

    private Integer proId;

    List<ProPeopleVO> proPeopleVOS;
}
