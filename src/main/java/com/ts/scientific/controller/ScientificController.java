package com.ts.scientific.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ts.scientific.config.BizException;
import com.ts.scientific.dto.DeptStatisticsDto;
import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.*;
import com.ts.scientific.mapper.ScientificExtendMapper;
import com.ts.scientific.mapper.ScientificProPeopleInfoMapper;
import com.ts.scientific.mapper.ScientificProPeopleMapper;
import com.ts.scientific.service.StatisticsDetailService;
import com.ts.scientific.service.impl.*;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author
 * @since 2020-02-16
 */
@RestController
@CrossOrigin
@RequestMapping("/scientific")
public class ScientificController {

        @Autowired
        private StatisticsDetailServiceImpl statisticsDetailServiceImpl;

        @Autowired
        private ScientificInfoConfServiceImpl scientificInfoConfServiceImpl;

        @Autowired
        private ScientificInfoServiceImpl scientificInfoServiceImpl;

        @Autowired
        private UserServiceImpl userServiceImpl;

        @Autowired
        private ScientificProServiceImpl scientificProServiceImpl;

        @Resource
        private ScientificProPeopleInfoMapper scientificProPeopleInfoMapper;

        @Resource
        private ScientificProPeopleMapper scientificProPeopleMapper;

        @Resource
        private ScientificExtendMapper scientificExtendMapper;
        /**
         * 科研绩效查询
         */
        @GetMapping("/getScientificDetail")
        public Object getScientificDetail(StatisticsDetailVO statisticsDetailVO){
                if (statisticsDetailVO.getCurrentPage() != null && statisticsDetailVO.getPageSize() != null) {
                        statisticsDetailVO.setCurrentPage((statisticsDetailVO.getCurrentPage() - 1) * statisticsDetailVO.getPageSize());
                        statisticsDetailVO.setPageSize(statisticsDetailVO.getPageSize());
                }
                List<StatisticsDetailDto> statisticsDetailDtos = statisticsDetailServiceImpl.getStatistics(statisticsDetailVO);
                statisticsDetailVO.setCurrentPage(null);
                statisticsDetailVO.setPageSize(null);
                int count = statisticsDetailServiceImpl.getStatistics(statisticsDetailVO).size();
                return RepResult.repResult(0, "成功", statisticsDetailDtos, count);
        }

        /**
         * 科研项目添加
         */
        @PostMapping("/addPro")
        public Object getAllUser(@RequestBody ProVO proVO, HttpServletRequest request){
             return  scientificProServiceImpl.addPro(proVO,request);
        }

        /**
         * 文件上传
         */
        @PostMapping("/uploadFile")
        public Object uploadFile(MultipartFile file){
                JSONObject jsonObject = new JSONObject();
                String path = "F:/bysj/apache-tomcat-8.5.50-file/webapps/file";
                if (!file.isEmpty()) {
                     String srcName =   UUID.randomUUID()+file.getOriginalFilename();
                        File newPath = new File(path, srcName);
                        if (!file.isEmpty()){
                                try {
                                        InputStream in = file.getInputStream();
                                        FileOutputStream out = new FileOutputStream(newPath);
                                        byte buffer[] = new byte[1024];
                                        int len = 0;
                                        while ((len = in.read(buffer)) > 0) {
                                                out.write(buffer, 0, len);
                                        }
                                        in.close();
                                        out.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                        throw new  BizException("失败");
                                }
                        }
                        jsonObject.put("src","http://localhost:8081/file/"+srcName);
                }
                return RepResult.repResult(0,"",jsonObject);
        }

        @PostMapping("/getAllUser")
        public Object getAllUser(@RequestBody  UserAllVO userAllVO){
                return userServiceImpl.getAllUser(userAllVO);
        }

        @GetMapping("/getAlldept")
        public Object getAlldept(){
            return userServiceImpl.getAlldept();
        }
        /**
         * 材料上报
         */
        @PostMapping("/insetMaterials")
        @Transactional(rollbackFor = Exception.class)
        public Object insetMaterials(@RequestBody ProPeopleMaterVO proPeopleMaterVO){
            ScientificProPeople proPeople = new ScientificProPeople();
            ScientificProPeopleInfo proPeopleInfo =  scientificProPeopleInfoMapper.selectOne(new QueryWrapper<ScientificProPeopleInfo>().lambda()
                    .eq(ScientificProPeopleInfo::getProId,proPeopleMaterVO.getProId())
                    .eq(ScientificProPeopleInfo::getUserId,proPeopleMaterVO.getUserId()));
            ScientificProPeopleInfo scientificProPeopleInfo = new ScientificProPeopleInfo();
            if (proPeopleInfo == null) {
                proPeople.setMaterialsStatus(4);
                scientificProPeopleMapper.update(proPeople, new QueryWrapper<ScientificProPeople>().lambda()
                        .eq(ScientificProPeople::getProPeopleId, proPeopleMaterVO.getProPeopleId()));
                scientificProPeopleInfo.setMaterials(proPeopleMaterVO.getMaterials());
                scientificProPeopleInfo.setProId(proPeopleMaterVO.getProId());
                scientificProPeopleInfo.setUserId(proPeopleMaterVO.getUserId());
                scientificProPeopleInfoMapper.insert(scientificProPeopleInfo);
            }else {
                scientificProPeopleInfo.setMaterials(proPeopleMaterVO.getMaterials());
                scientificProPeopleInfoMapper.update(scientificProPeopleInfo,new QueryWrapper<ScientificProPeopleInfo>().lambda()
                        .eq(ScientificProPeopleInfo::getProPeopleInfoId,proPeopleInfo.getProPeopleInfoId()));
            }
            return RepResult.repResult(0,"上报成功",null);

        }

        @GetMapping("/delProPeople")
        public Object delProPeople(Integer proPeopleId){
            scientificProPeopleMapper.delete(new QueryWrapper<ScientificProPeople>().lambda()
                    .eq(ScientificProPeople::getProPeopleId,proPeopleId));
            return RepResult.repResult(0,"成功",null);
        }

        /**
         * 查看申报材料
         */
        @GetMapping("/findMaterials")
        public Object materials(Integer proId,Integer userId){
            return RepResult.repResult(0,"成功",scientificProPeopleInfoMapper.selectOne(new QueryWrapper<ScientificProPeopleInfo>().lambda()
                    .eq(ScientificProPeopleInfo::getProId,proId)
                    .eq(ScientificProPeopleInfo::getUserId,userId)));
        }
    /**
         * 项目类别
         */
        @GetMapping("/getType")
        public Object getType(){
             return RepResult.repResult(0,"成功",scientificInfoServiceImpl.list(new QueryWrapper<ScientificInfo>().lambda().eq(ScientificInfo::getDelFlag, 0)));
        }

        /**
         * 计算条件查询
         */
        @GetMapping("/getCalculate")
        public Object getCalculate(Integer projectTypeId){
                return scientificInfoConfServiceImpl.getCalculateIds(projectTypeId);
        }

        @GetMapping("/getPro")
        public  Object getPro(FindProVO findProVO,HttpServletRequest request){
                int count = scientificInfoConfServiceImpl.count();
                return RepResult.repResult(0,"",scientificProServiceImpl.getPro(findProVO,request),count);
        }

        /**
         * 获取当前项目人员
         */
        @GetMapping("/getCurrentProPeople")
        public Object getCurrentProPeople(int current,int size,Integer proId){
                return scientificProServiceImpl.getCurrentProPeople(current,size,proId);
        }

    /**
     * 获取个人绩效
     */
    @GetMapping("/getPersonalPerformance")
    public Object getPersonalPerformance(Integer current,Integer size){
        return scientificProServiceImpl.getPersonalPerformance(current,size);
    }

    /**
     * 获取部门汇总
     */
    @GetMapping("/getDeptPerformance")
    public Object getDeptPerformance(Integer current,Integer size){
        User user = (User) WebUtils.getHttpSession().getAttribute("user");

        Map<String,Object> map = new HashMap<>();

        map.put("depId",user.getDepId());
        int count = scientificExtendMapper.getDeptStatistics(map).size();
        map.put("currentPage",(current - 1) * size);
        map.put("pageSize",size);
        List<DeptStatisticsDto> statisticsDtos = scientificExtendMapper.getDeptStatistics(map);
        return RepResult.repResult(0,"",statisticsDtos,count);
    }

    /**
     * 修改排名
     */
    @GetMapping("/editRank")
    public Object editRank(Integer proId,Integer userId,Integer rank){
        ScientificProPeople scientificProPeople = new ScientificProPeople();
        scientificProPeople.setRank(rank);
        scientificProPeopleMapper.update(scientificProPeople,new QueryWrapper<ScientificProPeople>().lambda()
                .eq(ScientificProPeople::getProId,proId)
                .eq(ScientificProPeople::getUserId,userId));
        return RepResult.repResult(0,"成功",null);
    }

    /**
     * 材料审核
     */
    @RequestMapping("audit")
    public Object audit(String id,String bank){
        return scientificProServiceImpl.getAudit(id,bank);
    }
    //===========科研信息开始===============

        /**
         * 加载科研信息列表数据
         */
        @GetMapping("/loadAllScientificInfo")
        public Object loadAllScientificInfo(ScientificInfoVo scientificInfoVo) {
                return scientificInfoServiceImpl.queryAllScientificInfo(scientificInfoVo);
        }


        /**
         * 添加科研信息
         *
         * @param scientificInfo
         * @return
         */
        @RequestMapping("/addScientificInfo")
        public Object addScientificInfo(ScientificInfo scientificInfo) {
                return scientificInfoServiceImpl.insertSelective(scientificInfo);
        }

       /**
         * 修改科研信息
         *
         * @param scientificInfoVo
         * @return
         */
        @RequestMapping("/updateScientificInfo")
        public Object updateScientificInfo(ScientificInfoVo scientificInfoVo) {
                return scientificInfoServiceImpl.updateScientificInfo(scientificInfoVo);
        }

        /**
         * 删除科研信息
         */
        @RequestMapping("/deleteScientificInfo")
        public Object deleteScientificInfo(String id) {
                return scientificInfoServiceImpl.deleteScientificInfo(id);
        }


    /**
     * 分配计算标准
     *
     * @param scientificInfoVo
     * @return
     */
    @RequestMapping("/saveProjectConf")
    public Object saveProjectConf(ScientificInfoVo scientificInfoVo) {
        return scientificInfoServiceImpl.saveProjectConf(scientificInfoVo);
    }


        /**
         * 加载计算标准列表数据
         */
        @GetMapping("/loadAllScientificInfoConf")
        public Object loadAllScientificInfoConf(ScientificInfoConfVo scientificInfoConfVo) {
                return scientificInfoConfServiceImpl.queryAllScientificInfoConf(scientificInfoConfVo);
        }


        /**
         * 添加计算标准
         *
         * @return
         */
        @RequestMapping("/addScientificInfoConf")
        public Object addScientificInfoConf(ScientificInfoConf scientificInfoConf) {
                return scientificInfoConfServiceImpl.insertSelective(scientificInfoConf);
        }

        /**
         * 修改计算标准
         *
         * @param scientificInfoConf
         * @return
         */
        @RequestMapping("/updateScientificInfoConf")
        public Object updateScientificInfoConf(ScientificInfoConf scientificInfoConf) {
                return scientificInfoConfServiceImpl.updateScientificInfoConf(scientificInfoConf);
        }

        /**
         * 删除计算标准
         */
        @RequestMapping("/deleteScientificInfoConf")
        public Object deleteScientificInfoConf(String id) {
                return scientificInfoConfServiceImpl.deleteScientificInfoConf(id);
        }

    /**
     * 加载角色管理里面的分配权限的权限列表并选中已拥有的权限
     */
    @RequestMapping("loadScientificInfoConf")
    public Object loadScientificInfoConf(String id) {
        return scientificInfoConfServiceImpl.loadScientificInfoConf(id);
    }



    /**
     * 加载用户项目详情列表数据
     */
    @RequestMapping("/loadDetile")
    public Object loadDetile(ProDetileVo proDetileVo) {
        return scientificInfoConfServiceImpl.loadDetile(proDetileVo);
    }


        //===========科研信息结束===============


}
