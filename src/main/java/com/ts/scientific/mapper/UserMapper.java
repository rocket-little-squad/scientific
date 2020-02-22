package com.ts.scientific.mapper;

import com.ts.scientific.dto.UserDto;
import com.ts.scientific.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.scientific.vo.UserVo;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-02-18
 */
public interface UserMapper extends BaseMapper<User> {

    List<UserDto> queryUserAll(UserVo userVo);

}
