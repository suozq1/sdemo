package com.suo.sdemo.buss.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.pojo.form.SysUserCreateForm;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;
import com.suo.sdemo.buss.sys.service.SysUserService;
import com.suo.sdemo.common.AppResponse;
import com.suo.sdemo.common.enmus.SysResourceEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/user", "/users"})
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

    @ApiOperation("新建或修改用户")
    @PutMapping("/")
    @RequiresPermissions(SysResourceEnum.CODE_RES_DATA_USER_ALL)
    public AppResponse<String> saveOrUpdateUser(@RequestBody @Validated SysUserCreateForm form,
        HttpServletRequest request) {
        AppResponse<String> r = AppResponse.success(request);
        sysUserService.saveOrUpdate(form);
        return r;
    }

    @ApiOperation("检查账号是否存在")
    @GetMapping("/email/{email}")
    public AppResponse<Boolean> ifExist(HttpServletRequest request, @PathVariable String email,
        Integer userId) {
        AppResponse<Boolean> r = AppResponse.success(request);
        r.setData(sysUserService.ifExist(email, userId));
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

}
