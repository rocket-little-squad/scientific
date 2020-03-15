package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Centre implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "centre_id", type = IdType.AUTO)
    private Integer centreId;

    private Integer roleId;

    private Integer authId;


}
