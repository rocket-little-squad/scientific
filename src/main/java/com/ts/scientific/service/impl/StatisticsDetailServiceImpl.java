package com.ts.scientific.service.impl;

import com.ts.scientific.dto.StatisticsDetailDto;
import com.ts.scientific.entity.StatisticsDetail;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.ScientificExtendMapper;
import com.ts.scientific.mapper.StatisticsDetailMapper;
import com.ts.scientific.service.StatisticsDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.StatisticsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统计表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class StatisticsDetailServiceImpl extends ServiceImpl<StatisticsDetailMapper, StatisticsDetail> implements StatisticsDetailService {

    @Autowired
    private ScientificExtendMapper scientificExtendMapper;
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Override
    public List<StatisticsDetailDto> getStatistics(StatisticsDetailVO statisticsDetailVO) {
        Map<String,List<String>> authCode = authServiceImpl.queryRoleAuth();
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        if (user.getRoleId() != 1 || authCode.get(user.getRoleId()) != null && !authCode.get(user.getRoleId()).contains("allPerformance")){
            statisticsDetailVO.setUserId(user.getUserId());
        }
        List<StatisticsDetailDto> statisticsDetailDtos =  scientificExtendMapper.getStatistics(statisticsDetailVO);
        for (StatisticsDetailDto detailDto : statisticsDetailDtos) {
            if (detailDto.getStandardScore().compareTo(detailDto.getScore())>0){
                detailDto.setIsPass("不通过");
            }else{
                detailDto.setIsPass("通过");
            }
        }
        return statisticsDetailDtos;
    }
}
