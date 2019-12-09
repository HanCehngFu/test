package com.nest_lot.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.nest_lot.model.SysUserBean;

/**
 * 判断是否记住登录状态拦截器
 * 
 * @author gongtianpei
 *
 */
public class ValidRememberMeFilter extends AccessControlFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		// 未认证登录并且记住登录状态
		if (!subject.isAuthenticated() && subject.isRemembered()) {
			Object key = subject.getPrincipals().getPrimaryPrincipal();
			SysUserBean userBean = new SysUserBean();
			BeanUtils.copyProperties(userBean, key);
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userBean.getUserLoginId(),
					userBean.getUserPwd());
			usernamePasswordToken.setRememberMe(true);
			subject.login(usernamePasswordToken);
		}
		return true;
	}

}
