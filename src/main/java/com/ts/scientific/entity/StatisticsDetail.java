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
 * 统计表
 * </p>
 *
 * @author 
 * @since 2020-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("statistics_detail")
public class StatisticsDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "statistics_detail_id", type = IdType.AUTO)
    private Integer statisticsDetailId;

    /**
     * 创建时间
     */
    private LocalDate createTime;

    /**
     * 积分
     */
    private BigDecimal score;

    private Integer userId;


}
