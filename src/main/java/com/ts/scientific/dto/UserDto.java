package com.ts.scientific.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserDto implements Serializable {

    private Integer userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 部门名字
     */
    private String depName;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色id
     */
    private Integer roleId;
}
