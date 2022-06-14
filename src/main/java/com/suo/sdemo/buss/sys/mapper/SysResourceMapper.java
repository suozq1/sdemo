package com.suo.sdemo.buss.sys.mapper;

import com.suo.sdemo.buss.sys.entity.SysResource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suozq
 * @since 2022-06-14
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {

	List<SysResource> findByRoleId(Integer roleId);

}
