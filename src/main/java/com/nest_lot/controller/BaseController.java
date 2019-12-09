package com.nest_lot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.nest_lot.config.ActiveMqConfig;
import com.nest_lot.config.SmsConfig;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.service.SysFuntreeInfoService;
import com.nest_lot.service.SysRoleFuntreeInfoService;
import com.nest_lot.service.SysRoleInfoServcie;
import com.nest_lot.service.SysUserInfoService;
import com.nest_lot.service.SysUserRoleInfoService;


@Controller
public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected SysUserInfoService sysUserInfoService;

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;


	

	@Autowired
	protected SysFuntreeInfoService funtreeInfoService;

	@Autowired
	protected SysRoleInfoServcie roleInfoServcie;


	@Autowired
	protected SysRoleFuntreeInfoService sysRoleFuntreeInfoService;

	@Autowired
	protected SysUserRoleInfoService sysUserRoleInfoService;

	

	@Autowired
	protected SmsConfig smsConfig;

	

	@Autowired
	protected ActiveMqConfig activeMqConfig;

	

	/**
	 * shiro Subject
	 * 
	 * @return
	 */
	protected Subject getSubject() {
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录用户SysUserBean对象
	 * 
	 * @return
	 */
	protected SysUserBean getSysUserBean() {
		SysUserBean sysUserBean = new SysUserBean();

		try {
			BeanUtils.copyProperties(sysUserBean, this.getSubject().getPrincipal());
		} catch (Exception e) {
			logger.error("", e);
		}
		return sysUserBean;
	}
}
