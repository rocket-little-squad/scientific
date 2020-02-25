package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProPeopleMaterVO extends ScientificProPeople implements Serializable {

    private String materials;


}
