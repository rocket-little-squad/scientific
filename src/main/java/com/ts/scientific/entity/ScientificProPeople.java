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
@TableName("scientific_pro_people")
public class ScientificProPeople implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 项目id
     */
    private Integer proId;

    private LocalDate createTime;

    private String createName;

    /**
     * 负责人排序
     */
    private Integer rank;

    /**
     * 提交状态1:审核通过 2:审核不通过 3:进行中
     */
    private Integer materialsStatus;


}
