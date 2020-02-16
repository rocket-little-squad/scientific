package com.ts.scientific.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 科研信息表
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scientific_info")
public class ScientificInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectTypeId;

    private String projectTypeName;

    /**
     * 规则
     */
    private String rule;

    /**
     * 时间分配规则(1:年 2:月 3:日)
     */
    private Integer ruleTime;

    private LocalDate createTime;

    private String createName;


}
