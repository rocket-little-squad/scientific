package com.ts.scientific.entity;

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
 * @since 2020-02-23
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
     * 项目状态 1:项目建立 2:项目进行中 3:项目完成 4:审核不通过 5:项目超时
     */
    private Integer proStatus;

    private LocalDate createTime;

    private Integer createId;

    /**
     * 项目编号
     */
    private String proNo;


}
