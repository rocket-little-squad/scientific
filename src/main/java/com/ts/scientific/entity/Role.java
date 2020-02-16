package com.ts.scientific.entity;

import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色名称
     */
    private String roleName;

    private LocalDate createTime;

    private String createName;

    /**
     * 权限id
     */
    private Integer authId;


}