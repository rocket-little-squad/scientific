package com.ts.scientific.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.scientific.entity.ScientificPro;
import com.ts.scientific.entity.ScientificProPeopleInfo;
import com.ts.scientific.vo.FindProVO;
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
    Object getPro(FindProVO findProVO, HttpServletRequest request);

    /**
     * 获取项目人员
     */
    Object getCurrentProPeople(int current,int size,Integer proId);
    /**
     * 提交申报材料
     */
    Object addProMaterials(ScientificProPeopleInfo scientificProPeopleInfo, HttpServletRequest request);

    /**
     * 项目材料状态更新
     */
    Object updateMaterialsStatus(Integer status,Integer proPeopleId);

    /**
     *  个人绩效
     */
    Object getPersonalPerformance(int current,int size);
}
