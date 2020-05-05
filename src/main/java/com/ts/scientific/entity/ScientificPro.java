package com.ts.scientific.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
@TableName("scientific_pro")
public class ScientificPro implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pro_id", type = IdType.AUTO)
    private Integer proId;

    private Integer projectTypeId;

    private Integer calculateId;

    private LocalDate startTime;

    private LocalDate endTime;

    /**
     * 项目状态 1:进行中 2:完成 3:审核不通过 4:超时 5:已结算
     */
    private Integer proStatus;

    private LocalDate createTime;

    private String createName;

    /**
     * 项目编号
     */
    private String proNo;

    private BigDecimal score;

    private Integer createId;


}
