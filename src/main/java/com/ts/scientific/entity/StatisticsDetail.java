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
 * 统计表
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("statistics_detail")
public class StatisticsDetail implements Serializable {

    private static final long serialVersionUID = 1L;

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
