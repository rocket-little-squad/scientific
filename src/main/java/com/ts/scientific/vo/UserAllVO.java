package com.ts.scientific.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ts.scientific.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserAllVO implements Serializable {

    private long current;

    private long size;

    private List<Integer> userIds;


}
