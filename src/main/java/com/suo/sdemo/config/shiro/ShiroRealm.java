package com.suo.sdemo.config.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.suo.sdemo.buss.sys.entity.SysResource;
import com.suo.sdemo.buss.sys.entity.SysRole;
import com.suo.sdemo.buss.sys.entity.SysUser;
import com.suo.sdemo.buss.sys.service.SysUserService;

public class ShiroRealm extends AuthorizingRealm {
	
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Lazy
	@Autowired
	SysUserService sysUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>();
		Set<String> permissions = new HashSet<>();
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		List<SysRole> sysRoles = sysUserService.getUserRoles(user.getId());
		for (SysRole r : sysRoles) {
			roles.add(r.getCode());
			List<SysResource> sysResources = sysUserService.getRoleResourcese(r.getRoleId());
			for (SysResource re : sysResources) {
				permissions.add(re.getCode());
			}
		}
		info.addRoles(roles);
		info.addStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		SysUser user = sysUserService.userLogin(token.getUsername(), new String(token.getPassword()));
		return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
	}

}
