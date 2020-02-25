package com.ts.scientific.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.scientific.config.BizException;
import com.ts.scientific.entity.Auth;
import com.ts.scientific.entity.Centre;
import com.ts.scientific.entity.Role;
import com.ts.scientific.entity.User;
import com.ts.scientific.mapper.AuthMapper;
import com.ts.scientific.mapper.CentreMapper;
import com.ts.scientific.mapper.RoleMapper;
import com.ts.scientific.mapper.UserMapper;
import com.ts.scientific.service.AuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.scientific.util.RepResult;
import com.ts.scientific.util.WebUtils;
import com.ts.scientific.vo.AuthVo;
import com.ts.scientific.vo.RoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-02-16
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth> implements AuthService {

    @Resource
    private AuthMapper authMapper;

    @Resource
    private CentreMapper centreMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Object queryAllAuth(AuthVo authVo) {
        LambdaQueryWrapper<Auth> queryWrapper = new LambdaQueryWrapper<Auth>()
                .eq(StringUtils.isNotBlank(authVo.getAuthName()), Auth::getAuthName, authVo.getAuthName())
                .eq(StringUtils.isNotBlank(authVo.getAuthCode()),Auth::getAuthCode,authVo.getAuthCode());
        Page<Auth> authPage = authMapper.selectPage(new Page<>(authVo.getPage(), authVo.getLimit()), queryWrapper);
        List<Auth> auths = authPage.getRecords();
        return RepResult.repResult(0, "查询成功", auths, (int) authPage.getTotal());
    }

    @Override
    public Object loadRoleAuth(String id){

        List<Auth> auths = authMapper.selectList(new QueryWrapper<Auth>());

        List<Centre> authId = centreMapper.selectList(new QueryWrapper<Centre>().eq("role_id", id));
        List<Map<String,Object>> list = new ArrayList<>();
        for (Auth r1 : auths) {
            Boolean LAY_CHECKED=false;
            for (Centre r2 : authId) {
                if(r1.getAuthId()==r2.getAuthId()) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            Map<String,Object> map=new HashMap<>();
            map.put("authId", r1.getAuthId());
            map.put("authName", r1.getAuthName());
            map.put("authCode", r1.getAuthCode());
            map.put("LAY_CHECKED", LAY_CHECKED);
            list.add(map);
        }
        return RepResult.repResult(0, "查询成功", list);
    }

    @Override
    public Object insertSelective(Auth record) {
        if (null == record) {
            throw new BizException("添加数据为空");
        }
        record.setCreateName(WebUtils.getCurrentUserName());
        record.setCreateTime(LocalDate.now());
        if (1 != authMapper.insert(record)) {
            throw new BizException("添加权限数据失败");
        }
        return RepResult.repResult(0, "添加成功", null);
    }

    @Override
    public Object deleteByPrimaryKey(Integer id) {
        if (null == id) {
            throw new BizException("删除id为空");
        }
        List<Centre> auth_id = centreMapper.selectList(new QueryWrapper<Centre>().eq("auth_id", id));
        if(null!=auth_id || auth_id.size()>0){
            return RepResult.repResult(0, "该权限正在被使用", null);
        }
        if (1 != authMapper.deleteById(id)) {
            throw new BizException("删除权限数据失败");
        }
        return RepResult.repResult(0, "删除成功", null);
    }

    @Override
    public Object updateByPrimaryKeySelective(AuthVo authVo) {
        if (null == authVo) {
            throw new BizException("修改数据为空");
        }
        if (1 != authMapper.updateById(authVo)) {
            throw new BizException("修改权限数据失败");
        }
        return RepResult.repResult(0, "修改成功", null);
    }

    public Object queryRoleAuth(){
        String currentUserName = WebUtils.getCurrentUserName();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", currentUserName));
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("role_id", user.getRoleId()));
        List<Centre> centres = centreMapper.selectList(new QueryWrapper<Centre>().eq("role_id", user.getRoleId()));
        Map<String,List<String>> roleAuths = new HashMap<String,List<String>>();
        List<String> auths = new ArrayList<>();
        for (Centre centre : centres) {
            Auth auth_id = authMapper.selectOne(new QueryWrapper<Auth>().eq("auth_id", centre.getAuthId()));
            auths.add(auth_id.getAuthCode());
        }
        roleAuths.put(role.getRoleId().toString(),auths);
        return RepResult.repResult(0,"查询成功",roleAuths);
    }
}
