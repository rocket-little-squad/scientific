package com.ts.scientific.entity;

import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 角色code
     */
    private String userCode;

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


}
