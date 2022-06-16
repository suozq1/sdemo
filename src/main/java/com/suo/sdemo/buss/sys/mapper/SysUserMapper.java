package com.suo.sdemo.buss.sys.mapper;

import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.pojo.form.SysUserSearchForm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suozq
 * @since 2022-06-14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	IPage<SysUser> userManageList(Page<?> page, SysUserSearchForm form);

	void deleteUsers(List<Integer> userIds);

}
