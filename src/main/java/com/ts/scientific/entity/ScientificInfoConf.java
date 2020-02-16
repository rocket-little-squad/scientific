package com.ts.scientific.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 科研信息配置表
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scientific_info_conf")
public class ScientificInfoConf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 计算id
     */
    private Integer calculateId;

    /**
     * 计算标准
     */
    private BigDecimal calculateScore;

    /**
     * 计算条件
     */
    private String calculateCondition;

    /**
     * 项目类别id
     */
    private Integer projectTypeId;

    private String createName;

    private LocalDate createTime;


}
