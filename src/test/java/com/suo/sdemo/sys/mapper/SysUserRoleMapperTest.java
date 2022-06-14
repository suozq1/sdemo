package com.suo.sdemo.sys.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.suo.sdemo.buss.sys.mapper.SysUserRoleMapper;

@SpringBootTest
public class SysUserRoleMapperTest {
	
	@Autowired
    SysUserRoleMapper sysUserRoleMapper;
	
	@Test
	@Transactional
	void selectCompanyIdByUserIdAndRCode() {
	   
	}
}
