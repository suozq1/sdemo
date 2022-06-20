package com.suo.sdemo.buss.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;
import com.suo.sdemo.buss.sys.service.SysUserService;
import com.suo.sdemo.common.AppResponse;
import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.enmus.SysResourceEnum;
import com.suo.sdemo.util.AppUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Valid
@RequestMapping({"/user"})
@Api(value = "用户模块", tags = "用户相关")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;


    @ApiOperation("用户列表")
    @PostMapping("/")
    @RequiresPermissions(SysResourceEnum.CODE_RES_DATA_USER_ALL)
    public AppResponse<IPage<SysUser>> userManageList(@RequestBody SysUserSearchForm form,
        HttpServletRequest request) {
        AppResponse<IPage<SysUser>> r = AppResponse.success(request);
        r.setData(sysUserService.userManageList(form));
        return r;
    }
    
    @ApiOperation("用户详情")
    @GetMapping("/{userId}")
    public AppResponse<SysUser> getUser(@PathVariable Integer userId,HttpServletRequest request){
        AppResponse<SysUser> r = AppResponse.success(request);
        if(!AppUtils.isPermitted(SysResourceEnum.CODE_RES_DATA_USER_ALL)&&!AppUtils.getCurrentUserId().equals(userId)) {
        	r.setErrorCode(ErrorCode.UNAUTHORIZED);
        	return r;
        }
        r.setData(sysUserService.findByUserId(userId));
        return r;
    }

    @ApiOperation("修改用户")
    @PostMapping("/{userId}")
    public AppResponse<String> updateUser(@PathVariable Integer userId, @RequestBody @Validated SysUser user, HttpServletRequest request) {
        AppResponse<String> r = AppResponse.success(request);
        if(!AppUtils.isPermitted(SysResourceEnum.CODE_RES_DATA_USER_ALL)&&!AppUtils.getCurrentUserId().equals(userId)) {
        	r.setErrorCode(ErrorCode.UNAUTHORIZED);
        	return r;
        }
        user.setUserId(userId);
        sysUserService.update(user);
        return r;
    }


    @ApiOperation("删除用户")
    @DeleteMapping("/{userId}")
    @RequiresPermissions(SysResourceEnum.CODE_RES_DATA_USER_ALL)
    public AppResponse<?> delUser(@PathVariable Integer userId, HttpServletRequest request) {
        AppResponse<?> r = AppResponse.success(request);
        sysUserService.delUser(userId);
        return r;
    }
    
    @ApiOperation("批量删除用户")
    @DeleteMapping("/")
    @RequiresPermissions(SysResourceEnum.CODE_RES_DATA_USER_ALL)
    public AppResponse<?> delUsers(@RequestBody List<Integer> userIds, HttpServletRequest request) {
        AppResponse<?> r = AppResponse.success(request);
        if(userIds==null || userIds.isEmpty()) {
        	r.setErrorCode(ErrorCode.PARAM_NULL);
        	return r;
        }
        sysUserService.delUsers(userIds);
        return r;
    }

}
