package org.springrain.frame.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.shiro.ShiroUser;

/**
 * 当前登录用户信息,可以在bean中调用获取当前登录用户信息,例如 SessionUser.getUserId()获取当前登录人的userId
 * 
 * @copyright {@link springrain}
 * @author <Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.common.SessionUser
 */
public class SessionUser {
	private static final Logger logger = LoggerFactory.getLogger(SessionUser.class);

	public SessionUser() {
	}

	public static ShiroUser getShiroUser() {
		Subject user = SecurityUtils.getSubject();
		if (user == null) {
			logger.warn("subject(user) is null, return null");
			return null;
		}
		ShiroUser shiroUser = (ShiroUser) user.getPrincipal();
		if (shiroUser == null) {
			logger.warn("shiroUser(user) is null, return null");
			return null;
		}
		return shiroUser;
	}

	public static Session getSession() {
		Subject user = SecurityUtils.getSubject();
		if (user == null) {
			logger.warn("subject(user) is null, return null");
			return null;
		}
		Session session = user.getSession();
		return session;
	}

	public static String getUserId() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			logger.warn("shiroUser(user) is null, return null");
			return null;
		}
		logger.debug("shiroUser(user) id [{}]", shiroUser.getId());
		return shiroUser.getId();
	}

	public static String getUserCode() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			logger.warn("shiroUser(user) is null, return null");
			return null;
		}
		logger.debug("shiroUser(user) account [{}]", shiroUser.getAccount());
		return shiroUser.getAccount();
	}

	public static String getUserName() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			logger.warn("shiroUser(user) is null, return null");
			return null;
		}
		logger.debug("shiroUser(user) name [{}]", shiroUser.getName());
		return shiroUser.getName();
	}

	public static String getEmail() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			logger.warn("shiroUser(user) is null, return null");
			return null;
		}
		logger.debug("shiroUser(user) email [{}]", shiroUser.getEmail());
		return shiroUser.getEmail();
	}

}
