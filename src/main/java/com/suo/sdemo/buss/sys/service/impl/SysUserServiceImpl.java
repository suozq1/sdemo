package com.suo.sdemo.buss.sys.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suo.sdemo.buss.sys.entity.SysResource;
import com.suo.sdemo.buss.sys.entity.SysRole;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.mapper.SysResourceMapper;
import com.suo.sdemo.buss.sys.mapper.SysRoleMapper;
import com.suo.sdemo.buss.sys.mapper.SysUserMapper;
import com.suo.sdemo.buss.sys.mapper.SysUserRoleMapper;
import com.suo.sdemo.buss.sys.pojo.form.SysUserCreateForm;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;
import com.suo.sdemo.buss.sys.service.SysUserService;
import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.exception.AppException;
import com.suo.sdemo.util.AppUtils;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysResourceMapper sysResourceMapper;


    @Override
    public SysUser findByEmail(String email) {
        Wrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, email);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 用户登录验证
     * 
     * @param userName
     * @param password
     * @return
     */
    @Override
    public SysUser userLogin(String email, String password) {
        SysUser user = findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD);
        }
        if (AppUtils.DEL_TRUE.equals(user.getDeleted())) {
            throw new AppException(ErrorCode.USER_DEL_OR_QUIT);
        }
        if (!password.equalsIgnoreCase(user.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_USERNAME_OR_PASSWORD);
        }
    
        // 配置权限,用于前端判断权限
        Map<String, SysResource> userResources = new LinkedHashMap<>();
        List<SysRole> sysRoles = getUserRoles(user.getId());
        for (SysRole r : sysRoles) {
            List<SysResource> sysResources = getRoleResourcese(r.getRoleId());
            for (SysResource re : sysResources) {
                if (!userResources.containsKey(re.getCode())) {
                    userResources.put(re.getCode(), re);
                }
            }
        }
        user.setUserResources(userResources);
        return user;
    }

    @Override
    public List<SysRole> getUserRoles(Integer userId) {
        return sysRoleMapper.findByUserId(userId);
    }

    @Override
    public List<SysResource> getRoleResourcese(Integer roleId) {
        return sysResourceMapper.findByRoleId(roleId);
    }

  

    @Override
    public IPage<SysUser> userManageList(SysUserSearchForm form) {
        return sysUserMapper.userManageList(form.getPage(), form);
    }

    @Override
    @Transactional
    public void saveOrUpdate(SysUserCreateForm form) {
        Integer userId = AppUtils.getCurrentUser().getUserId();
        LocalDateTime now = LocalDateTime.now();
        
    }

    @Override
    @Transactional
    public void delUser(Integer userId) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setDeleted(AppUtils.DEL_TRUE);
        sysUserMapper.updateById(user);
    }
    
    @Override
    public Boolean ifExist(String email, Integer userId) {
        Boolean r = false;
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getEmail, email);
        SysUser user = sysUserMapper.selectOne(query);
        if (user != null) {
            if (userId == null || !user.getUserId().equals(userId)) {
                r = true;
            }
        }
        return r;
    }

   
//    @Override
//    public List<SysUserRoleVo> getAllUserRoles(Integer userId) {
//        List<SysUserRoleVo> list = sysRoleMapper.selectAll(AppUtils.IS_DEL_FALSE);
//        list.forEach(x -> {
//            if (SysRole.COMPANY_IN.equals(x.getInCompany())) {
//                List<Company> companys = sysUserRoleMapper.selectByUserIdAndRoleId(userId, x.getRoleId());
//                if (companys != null && !companys.isEmpty()) {
//                    x.setChecked(true);
//                    x.setCompanys(companys);
//                }
//            } else {
//                LambdaQueryWrapper<SysUserRole> q = new LambdaQueryWrapper<>();
//                q.eq(SysUserRole::getRoleId, x.getRoleId()).eq(SysUserRole::getUserId, userId);
//                List<SysUserRole> urs = sysUserRoleMapper.selectList(q);
//                if (urs != null && !urs.isEmpty()) {
//                    x.setChecked(true);
//                }
//            }
//        });
//        return list;
//    }


   
}
