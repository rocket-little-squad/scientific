package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProPeopleVO extends ScientificProPeople {

    private String userName;

}
