package com.suo.sdemo.sys.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.mapper.SysUserMapper;

@SpringBootTest
public class SysUserMapperTest {
	
	@Autowired
    SysUserMapper sysUserMapper;
	
	@Test
	@Transactional
	void selectList() {
		List<SysUser> userList = sysUserMapper.selectList(null);
		Assert.assertEquals("",userList.get(0).getNickname(), "suozq");
		SysUser sysUser = new SysUser();
		sysUser.setNickname("苑高龙");
		sysUser.setEmail("account");
		sysUser.setPassword("");
		sysUserMapper.insert(sysUser);
		
		
//		int a = 1/0;
		
		userList.get(0).setNickname("suozq");
		sysUserMapper.updateById(userList.get(0));
		
	}

}
