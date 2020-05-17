package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.lang.IntegerUtils;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.*;
import com.ts.scientific.mapper.*;
import com.ts.scientific.service.ScientificProService;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScientificProServiceImpl extends ServiceImpl<ScientificProMapper, ScientificPro> implements ScientificProService {

    @Autowired
    private ScientificProPeopleMapper scientificProPeopleMapper;
    @Autowired
    private ScientificProPeopleInfoMapper scientificProPeopleInfoMapper;
    @Autowired
    private ScientificProMapper scientificProMapper;
    @Autowired
    private ScientificInfoMapper scientificInfoMapper;
    @Autowired
    private ScientificInfoConfMapper scientificInfoConfMapper;
    @Autowired
    private ScientificInfoCentreMapper scientificInfoCentreMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthServiceImpl authServiceImpl;
    @Autowired
    private StatisticsDetailMapper statisticsDetailMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private TitleMapper titleMapper;


    @Override
    public Object addPro(ProVO proVO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ScientificPro scientificPro = new ScientificPro();
        LocalDate now = LocalDate.now();
        if (now.isBefore(proVO.getStartTime())){
            proVO.setProStatus(1);
        }
        if (now.isAfter(proVO.getEndTime())){
           throw new BizException("结束时间不能小于当前时间");
        }
        proVO.setProNo(String.valueOf(System.currentTimeMillis()));
        proVO.setCreateTime(now);
        proVO.setCreateId(user.getUserId());
        proVO.setCreateName(user.getUserName());
        BeanUtils.copyProperties(proVO,scientificPro);
        this.save(scientificPro);
        List<ScientificProPeople> scientificProPeopleList = new ArrayList<>();
        ScientificProPeople proPeople  = null;
        int i = 1;
        for (Integer userId : proVO.getUserIds()) {
            proPeople = new ScientificProPeople();
            proPeople.setProId(scientificPro.getProId());
            proPeople.setCreateName(user.getUserName());
            proPeople.setUserId(userId);
            proPeople.setCreateTime(now);
            proPeople.setMaterialsStatus(3);
            proPeople.setRank(i++);
            if (user.getUserId().equals(userId)){
                proPeople.setIsPrincipal(0);
            }else{
                proPeople.setIsPrincipal(1);
            }
            scientificProPeopleList.add(proPeople);
        }
        if (scientificProPeopleList.size()>0) {
            addPeople(scientificProPeopleList, scientificProPeopleList.size()-1);
        }
        return RepResult.repResult(0,"申报成功",null);
    }

    @Override
    public Object getPro(FindProVO findProVO, HttpServletRequest request) {

        Map<String,List<String>> authCode = authServiceImpl.queryRoleAuth();
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
//        String aCode=
//        if (authCode.get(user.getRoleId())!=null){
//            ///proPeopleVO.setFlag(1);
//              authCode.get(user.getRoleId()).contains("allPro");
//        }
        IPage<ScientificPro> page = new Page<>();
        page.setCurrent(findProVO.getCurrent());
        page.setSize(findProVO.getSize());
        List<ProSeeVO> proSeeVOS = new ArrayList<>();
        List<ScientificProPeople>  proPeopleIds =  scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda()
                .eq(ScientificProPeople::getUserId,user.getUserId()));
        LambdaQueryWrapper<ScientificPro> qw = new LambdaQueryWrapper<>();
        if (findProVO.getProjectTypeId()!=null){
            qw.eq(ScientificPro::getProjectTypeId,findProVO.getProjectTypeId());
        }
        if (user.getRoleId() != 1) {
            qw.and(w -> w.eq(ScientificPro::getCreateId, user.getUserId()).or().in(proPeopleIds != null && proPeopleIds.size() > 0, ScientificPro::getProId, proPeopleIds.stream().map(ScientificProPeople::getProId).collect(Collectors.toList())));
            //qw.in(proPeopleIds != null && proPeopleIds.size() > 0, ScientificPro::getProId, proPeopleIds.stream().map(ScientificProPeople::getProId).collect(Collectors.toList()));
        }
        List<ScientificPro> scientificPros = scientificProMapper.selectPage(page,qw).getRecords();
        ProSeeVO proSeeVO = null;
        for (ScientificPro pro : scientificPros) {
            proSeeVO = new ProSeeVO();
            proSeeVO.setProId(pro.getProId());
            proSeeVO.setCalculateName(scientificInfoConfMapper.selectOne(new QueryWrapper<ScientificInfoConf>().lambda()
            .eq(ScientificInfoConf::getCalculateId,pro.getCalculateId())).getCalculateCondition());
            proSeeVO.setProType(scientificInfoMapper.selectOne(new QueryWrapper<ScientificInfo>().lambda()
                    .eq(ScientificInfo::getProjectTypeId,pro.getProjectTypeId())).getProjectTypeName());
            proSeeVO.setCreateName(pro.getCreateName());
            proSeeVO.setProNo(pro.getProNo());
            proSeeVO.setEnd(pro.getEndTime());
            proSeeVO.setStart(pro.getStartTime());
            proSeeVO.setProStatus(pro.getProStatus());
            List<ScientificProPeople> list = scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda()
                    .eq(ScientificProPeople::getProId,pro.getProId()));
            List<ProPeopleVO> proPeopleVOS = new ArrayList<>();
//            for (ScientificProPeople proPeople : list) {
//                ProPeopleVO proPeopleVO = new ProPeopleVO();
//                BeanUtils.copyProperties(proPeople,proPeopleVO);
//                proPeopleVO.setUserName(userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId,proPeople.getUserId())).getUserName());
//                proPeopleVOS.add(proPeopleVO);
//            }
            proSeeVO.setProPeopleVOS(proPeopleVOS);
            proSeeVOS.add(proSeeVO);
        }
        return proSeeVOS;
    }


   public Object getCurrentProPeople(int current,int size,Integer proId){
        Map<String,List<String>> authCode = authServiceImpl.queryRoleAuth();
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        IPage<ScientificProPeople> iPage = new Page<>();
        iPage.setCurrent(current);
        iPage.setSize(size);
        ScientificPro scientificPro =  scientificProMapper.selectOne(new QueryWrapper<ScientificPro>().lambda()
                .eq(ScientificPro::getProId,proId));
        IPage<ScientificProPeople> proPeopleIPage = scientificProPeopleMapper.selectPage(iPage,new QueryWrapper<ScientificProPeople>().lambda()
                .eq(ScientificProPeople::getProId,proId));
        List<ScientificProPeople> list = proPeopleIPage.getRecords();
        List<ProPeopleVO> proPeopleVOS = new ArrayList<>();
        for (ScientificProPeople proPeople : list) {
            ProPeopleVO proPeopleVO = new ProPeopleVO();
            BeanUtils.copyProperties(proPeople,proPeopleVO);
            if (user.getRoleId()==1 || user.getUserId() == scientificPro.getCreateId() || authCode.get(user.getRoleId())!=null && authCode.get(user.getRoleId()).contains("deletePeople")){
                proPeopleVO.setFlag(1);
            }
            if (user.getRoleId()==1 || user.getUserId() == scientificPro.getCreateId()){
                proPeopleVO.setUserFlag(1);
            }
            if (user.getRoleId()==1 || user.getUserId() == proPeople.getUserId()){
                proPeopleVO.setSeeFlag(1);
            }
            if (user.getRoleId()==1 || user.getUserId() == scientificPro.getCreateId() || authCode.get(user.getRoleId())!=null && authCode.get(user.getRoleId()).contains("audit")){
                proPeopleVO.setAuditFlag(1);
            }
            User us =  userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserId,proPeople.getUserId()));
            proPeopleVO.setProStatus(scientificPro.getProStatus());
            proPeopleVO.setUserName(us.getUserName());
            if (proPeople.getIsPrincipal() == 0){
                proPeopleVO.setPrincipal(us.getUserName());
                proPeopleVO.setUserName(null);
            }
            proPeopleVOS.add(proPeopleVO);
        }
        return RepResult.repResult(0,"",proPeopleVOS,Integer.valueOf(String.valueOf(proPeopleIPage.getTotal())));
    }

    public Object getAudit(String id,String bank){
        ScientificProPeople scientificProPeople = scientificProPeopleMapper.selectById(id);
        scientificProPeople.setMaterialsStatus(Integer.parseInt(bank));
        int i = scientificProPeopleMapper.updateById(scientificProPeople);
        List<ScientificProPeople> pro_id = scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().eq("pro_id", scientificProPeople.getProId()));
        boolean kens = true;
        for (ScientificProPeople proPeople : pro_id) {
            if(proPeople.getMaterialsStatus()!=1){
                kens = false;
            }
        }
//        if(kens){
//            ScientificPro scientificPro = scientificProMapper.selectById(scientificProPeople.getProId());
//            ScientificPro scientificPro1 = scientificPro.setProStatus(2);
//            int i1 = scientificProMapper.updateById(scientificPro1);
//        }
        return RepResult.repResult(0,"修改成功",null);
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

    @Override
    public Object getPersonalPerformance(int current, int size) {
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        IPage performanceVOIPage = new Page<>();
        performanceVOIPage.setCurrent(current);
        performanceVOIPage.setSize(size);
        IPage<StatisticsDetail> page = statisticsDetailMapper.selectPage(performanceVOIPage,new QueryWrapper<StatisticsDetail>().lambda()
        .eq(StatisticsDetail::getUserId,user.getUserId()));

        List<PersonalPerformanceVO> vos = new ArrayList<>();
        PersonalPerformanceVO vo = null;
        for (StatisticsDetail record : page.getRecords()) {
            vo = new PersonalPerformanceVO();
            BeanUtils.copyProperties(record,vo);
            ScientificPro scientificPro = scientificProMapper.selectOne(new QueryWrapper<ScientificPro>().lambda().eq(ScientificPro::getProId,record.getProId()));
            List<ScientificProPeople>  proPeopleIds =  scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda()
                    .eq(ScientificProPeople::getUserId,user.getUserId())
                    .eq(ScientificProPeople::getProId,record.getProId()));
            ScientificInfo scientificInfo =  scientificInfoMapper.selectOne(new QueryWrapper<ScientificInfo>().lambda()
                    .eq(ScientificInfo::getProjectTypeId,scientificPro.getProjectTypeId()));
            vo.setProNo(scientificPro.getProNo());
            if (proPeopleIds.size()>0 && proPeopleIds.get(0).getIsPrincipal() == 0 ){
                vo.setPrincipal("负责人");
            }else {
                vo.setPrincipal("参与者");
            }
            vo.setStandardScore(user.getStandardScore());
            vo.setTypeName(scientificInfo.getProjectTypeName());
            vos.add(vo);
        }
        return RepResult.repResult(0,"",vos,Integer.valueOf(String.valueOf(page.getTotal())));
    }

    @Override
    public Object getTotalScore() {
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
      List<StatisticsDetail> statisticsDetails = statisticsDetailMapper.selectList(new QueryWrapper<StatisticsDetail>().lambda()
                .eq(StatisticsDetail::getUserId,user.getUserId()));
        BigDecimal sum = BigDecimal.ZERO;
      if (!statisticsDetails.isEmpty()){
         sum = statisticsDetails.stream().map(StatisticsDetail::getScore).reduce(BigDecimal::add).get();
      }
        Department department =   departmentMapper.selectOne(new QueryWrapper<Department>().lambda()
                .eq(Department::getId,user.getDepId()));

       Title title = titleMapper.selectOne(new QueryWrapper<Title>().lambda()
                .eq(Title::getId,user.getTitleId()));
        TotalScoreVO totalScoreVO = new TotalScoreVO();
        totalScoreVO.setDepName(department.getDepName());
        totalScoreVO.setTitle(title.getTitleName());
        totalScoreVO.setTotalScore(sum);
        return RepResult.repResult(0,"",totalScoreVO);
    }

    @Override
    public Object getAuditPro(FindProVO findProVO, HttpServletRequest request) {

        Map<String,List<String>> authCode = authServiceImpl.queryRoleAuth();
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
//        String aCode=
//        if (authCode.get(user.getRoleId())!=null){
//            ///proPeopleVO.setFlag(1);
//              authCode.get(user.getRoleId()).contains("allPro");
//        }
        IPage<ScientificPro> page = new Page<>();
        page.setCurrent(findProVO.getCurrent());
        page.setSize(findProVO.getSize());
        List<ProSeeVO> proSeeVOS = new ArrayList<>();
        List<ScientificProPeople>  proPeopleIds =  scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda()
                .eq(ScientificProPeople::getUserId,user.getUserId()));
        LambdaQueryWrapper<ScientificPro> qw = new LambdaQueryWrapper<>();
        if (findProVO.getProjectTypeId()!=null){
            qw.eq(ScientificPro::getProjectTypeId,findProVO.getProjectTypeId());
        }
//        if (user.getRoleId() != 1) {
//            qw.and(w -> w.eq(ScientificPro::getCreateId, user.getUserId()).or().in(proPeopleIds != null && proPeopleIds.size() > 0, ScientificPro::getProId, proPeopleIds.stream().map(ScientificProPeople::getProId).collect(Collectors.toList())));
//        }
        List<ScientificPro> scientificPros = scientificProMapper.selectPage(page,qw).getRecords();
        ProSeeVO proSeeVO = null;
        for (ScientificPro pro : scientificPros) {
            proSeeVO = new ProSeeVO();
            proSeeVO.setProId(pro.getProId());
            proSeeVO.setCalculateName(scientificInfoConfMapper.selectOne(new QueryWrapper<ScientificInfoConf>().lambda()
                    .eq(ScientificInfoConf::getCalculateId,pro.getCalculateId())).getCalculateCondition());
            proSeeVO.setProType(scientificInfoMapper.selectOne(new QueryWrapper<ScientificInfo>().lambda()
                    .eq(ScientificInfo::getProjectTypeId,pro.getProjectTypeId())).getProjectTypeName());
            proSeeVO.setCreateName(pro.getCreateName());
            proSeeVO.setProNo(pro.getProNo());
            proSeeVO.setEnd(pro.getEndTime());
            proSeeVO.setStart(pro.getStartTime());
            proSeeVO.setProStatus(pro.getProStatus());
            List<ScientificProPeople> list = scientificProPeopleMapper.selectList(new QueryWrapper<ScientificProPeople>().lambda()
                    .eq(ScientificProPeople::getProId,pro.getProId()));
            List<ProPeopleVO> proPeopleVOS = new ArrayList<>();
            proSeeVO.setProPeopleVOS(proPeopleVOS);
            proSeeVOS.add(proSeeVO);
        }
        return proSeeVOS;
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
