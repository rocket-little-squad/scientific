package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FindProVO implements Serializable {

    private Integer projectTypeId;

    private int current;

    private int size;

    private String createName;

}
