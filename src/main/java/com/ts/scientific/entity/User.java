package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "user_id", type = IdType.AUTO)
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


}
