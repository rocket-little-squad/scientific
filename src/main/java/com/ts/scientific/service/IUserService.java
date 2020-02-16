package com.ts.scientific.service;

import com.ts.scientific.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface IUserService extends IService<User> {


    /**
     * 查询所有用户显示列表
     */
    Object queryAllUser(UserVo userVo);


    /**
     * 添加用户
     */
    Object insertSelective(User record);



    /**
     * 修改用户
     * @param record
     * @return
     */
    Object updateByPrimaryKeySelective(User record);




    /**
     * 删除用户
     * @param id
     * @return
     */
    Object deleteByPrimaryKey(Integer id);
}
