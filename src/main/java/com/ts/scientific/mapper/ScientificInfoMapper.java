package com.ts.scientific.mapper;

import com.ts.scientific.dto.ScientificInfoDto;
import com.ts.scientific.entity.ScientificInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.scientific.vo.ScientificInfoVo;

import java.util.List;

/**
 * <p>
 * 科研信息表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-22
 */
public interface ScientificInfoMapper extends BaseMapper<ScientificInfo> {

    int count(ScientificInfoVo scientificInfoVo);

    List<ScientificInfoDto> queryAllScientificInfo(ScientificInfoVo scientificInfoVo);

}
