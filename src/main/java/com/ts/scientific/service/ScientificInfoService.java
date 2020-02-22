package com.ts.scientific.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.entity.ScientificInfo;
import com.ts.scientific.vo.ScientificInfoVo;

/**
 * <p>
 * 科研信息表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface ScientificInfoService extends IService<ScientificInfo> {

    Object queryAllScientificInfo(ScientificInfoVo scientificInfoVo);

}
