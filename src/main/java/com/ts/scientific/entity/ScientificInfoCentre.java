package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-05-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scientific_info_centre")
public class ScientificInfoCentre implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "scientific_info_centre_id", type = IdType.AUTO)
    private Integer scientificInfoCentreId;

    /**
     * 类别id
     */
    private Integer projectTypeId;

    /**
     * 计算条件id
     */
    private Integer calculateId;

    /**
     * 0 否 1 是
     */
    private Integer delFlag;


}
