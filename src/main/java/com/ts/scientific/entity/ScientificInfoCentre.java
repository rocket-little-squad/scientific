package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scientific_info_centre")
public class ScientificInfoCentre implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer scientificInfoCentreId;

    /**
     * 类别id
     */
    private Integer projectTypeId;

    /**
     * 计算条件id
     */
    private Integer calculateId;


}
