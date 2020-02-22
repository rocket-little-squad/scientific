package com.ts.scientific.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.entity.ScientificPro;
import com.ts.scientific.entity.ScientificProPeopleInfo;
import com.ts.scientific.vo.ProVO;

import javax.servlet.http.HttpServletRequest;

public interface ScientificProService extends IService<ScientificPro> {

    /**
     * 申报项目
     */
    Object addPro(ProVO proVO, HttpServletRequest request);


    /**
     * 获取所有项目
     */
    Object getPro();

    /**
     * 提交申报材料
     */
    Object addProMaterials(ScientificProPeopleInfo scientificProPeopleInfo, HttpServletRequest request);

    /**
     * 项目审核
     */

}