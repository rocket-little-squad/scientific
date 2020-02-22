package com.ts.scientific.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.ts.scientific.entity.Auth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.entity.Role;
import com.ts.scientific.vo.AuthVo;
import com.ts.scientific.vo.RoleVo;

import java.util.Map;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface AuthService extends IService<Auth> {

    /**
     * 查询所有权限
     */
    Object queryAllAuth(AuthVo authVo);

    /**
     * 加载角色拥有所有权限
     * @param id
     * @return
     */
    Object loadRoleAuth(String id);

    /**
     * 添加权限
     */
    Object insertSelective(Auth record);

    /**
     * 删除权限
     */
    Object deleteByPrimaryKey(Integer id);

    /**
     * 修改权限
     * @return
     */
    Object updateByPrimaryKeySelective(AuthVo authVo);


}
