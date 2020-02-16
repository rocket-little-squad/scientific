package com.ts.scientific.service;

import com.ts.scientific.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
public interface UserService extends IService<User> {

    Object login(String email, String password, HttpServletRequest request);

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
     * 保存用户和角色之间的关系
     */

    Object saveUserRole(UserVo userVo);


    /**
     * 删除用户
     * @param id
     * @return
     */
    Object deleteByPrimaryKey(String id);
}
