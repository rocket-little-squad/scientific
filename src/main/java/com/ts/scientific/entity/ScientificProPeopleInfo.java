package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
 * @since 2020-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scientific_pro_people_info")
public class ScientificProPeopleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer proId;

    /**
     * 填报数据
     */
    private String materials;

    /**
     * 佐证材料
     */
    private String evidenceMaterials;

    private LocalDate createTime;

    private String createName;


}
