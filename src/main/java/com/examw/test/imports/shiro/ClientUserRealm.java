package com.examw.test.imports.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 客户端用户认证。
 * 
 * @author yangyong
 * @since 2014年8月26日
 */
public class ClientUserRealm extends AuthorizingRealm {
	private static final Logger logger = Logger.getLogger(ClientUserRealm.class);
	/*
	 * 授权信息。
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();
		
		return authorization;
	}
	/*
	 * 用户验证。
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.debug(String.format("token:[%s]", token.getClass()));
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String username = usernamePasswordToken.getUsername(),
				  pwd = new SimpleHash("md5", new String(usernamePasswordToken.getPassword()), ByteSource.Util.bytes(username), 2).toHex();
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配。
		return new SimpleAuthenticationInfo(username,pwd,ByteSource.Util.bytes(username),this.getName());
	}
}