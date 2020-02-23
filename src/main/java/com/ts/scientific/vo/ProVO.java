package com.ts.scientific.vo;

import com.ts.scientific.entity.ScientificPro;
import com.ts.scientific.entity.ScientificProPeople;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProVO extends ScientificPro implements Serializable {

    private List<Integer> userIds;
}
