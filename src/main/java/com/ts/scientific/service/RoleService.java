package com.ts.scientific.service;

import com.ts.scientific.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.vo.RoleVo;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询所有角色
     */
    Object queryAllRole(RoleVo roleVo);

    /**
     * 添加角色
     */
    Object insertSelective(Role record);

    /**
     * 删除角色
     */
    Object deleteByPrimaryKey(Integer id);

    /**
     * 修改角色
     * @return
     */
    Object updateByPrimaryKeySelective(RoleVo roleVo);

    /**
     * 保存角色与权限之间的关系
     * @param roleVo
     * @return
     */
    Object saveRoleAuth(RoleVo roleVo);



}
