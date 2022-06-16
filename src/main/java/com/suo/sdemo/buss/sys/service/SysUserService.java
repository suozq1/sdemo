package com.suo.sdemo.buss.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suo.sdemo.buss.sys.entity.SysResource;
import com.suo.sdemo.buss.sys.entity.SysRole;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;

public interface SysUserService {

    SysUser findByEmail(String email);
    
    void insert(SysUser user);

    SysUser userLogin(String userName, String password);

    List<SysResource> getRoleResourcese(Integer roleId);

    IPage<SysUser> userManageList(SysUserSearchForm form);

    void update(SysUser user);

    void delUser(Integer userId);

	List<SysRole> getUserRoles(Integer id);

	Boolean ifExist(String email, Integer userId);

	SysUser findByUserId(Integer userId);

}
