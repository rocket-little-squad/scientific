package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.entity.ScientificPro;
import com.ts.scientific.entity.ScientificProPeople;
import com.ts.scientific.entity.ScientificProPeopleInfo;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.ScientificProMapper;
import com.ts.scientific.mapper.ScientificProPeopleInfoMapper;
import com.ts.scientific.mapper.ScientificProPeopleMapper;
import com.ts.scientific.service.ScientificProService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.vo.ProVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScientificProServiceImpl extends ServiceImpl<ScientificProMapper, ScientificPro> implements ScientificProService {

    @Autowired
    private ScientificProPeopleMapper scientificProPeopleMapper;
    @Autowired
    private ScientificProPeopleInfoMapper scientificProPeopleInfoMapper;

    @Override
    public Object addPro(ProVO proVO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ScientificPro scientificPro = new ScientificPro();
        BeanUtils.copyProperties(proVO,scientificPro);
        LocalDate now = LocalDate.now();
        if (now.isBefore(proVO.getStartTime())){
            proVO.setProStatus(1);
        }else {
            proVO.setProStatus(2);
        }
        this.save(scientificPro);
        List<ScientificProPeople> scientificProPeopleList = new ArrayList<>();
        ScientificProPeople proPeople  = null;
        for (Integer userId : proVO.getUserIds()) {
            proPeople = new ScientificProPeople();
            proPeople.setProId(scientificPro.getProId());
            proPeople.setCreateName(user.getUserName());
            proPeople.setUserId(userId);
            proPeople.setCreateTime(now);
            proPeople.setMaterialsStatus(3);
        }
        addPeople(scientificProPeopleList,scientificProPeopleList.size());
        return RepResult.repResult(0,"申报成功",null);
    }

    @Override
    public Object getPro() {
        return null;
    }

    @Override
    public Object addProMaterials(ScientificProPeopleInfo scientificProPeopleInfo, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        scientificProPeopleInfo.setCreateName(user.getUserName());
        scientificProPeopleInfo.setCreateTime(LocalDate.now());
        scientificProPeopleInfo.setUserId(user.getUserId());
        scientificProPeopleInfoMapper.insert(scientificProPeopleInfo);
        return RepResult.repResult(0,"上报成功",null);
    }

    @Override
    public Object updateMaterialsStatus(Integer status, Integer proPeopleId) {
        ScientificProPeople scientificProPeople = new ScientificProPeople();
        scientificProPeople.setMaterialsStatus(status);
        return scientificProPeopleMapper.update(scientificProPeople,new QueryWrapper<ScientificProPeople>().lambda()
                .eq(ScientificProPeople::getProPeopleId,proPeopleId));
    }

    /**
     * 使用递归算法循环添加
     */
    private Boolean addPeople(List<ScientificProPeople> proPeople,Integer index){
        scientificProPeopleMapper.insert(proPeople.get(index));
        index--;
        if (index == -1){
            return true;
        }
        return addPeople(proPeople,index);
    }

}
