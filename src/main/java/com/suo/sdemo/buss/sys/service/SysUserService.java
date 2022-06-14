package com.suo.sdemo.buss.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suo.sdemo.buss.sys.entity.SysResource;
import com.suo.sdemo.buss.sys.entity.SysRole;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.entity.SysUserRole;
import com.suo.sdemo.buss.sys.pojo.form.SysUserCreateForm;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;

public interface SysUserService {

    SysUser findByEmail(String email);

    SysUser userLogin(String userName, String password);

    List<SysResource> getRoleResourcese(Integer roleId);

    IPage<SysUser> userManageList(SysUserSearchForm form);

    void saveOrUpdate(SysUserCreateForm form);

    void delUser(Integer userId);

//    List<SysUserRole> getAllUserRoles(Integer userId);

	List<SysRole> getUserRoles(Integer id);

	Boolean ifExist(String email, Integer userId);

}
