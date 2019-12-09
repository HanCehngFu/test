package com.nest_lot.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nest_lot.constant.ResultBase;
import com.nest_lot.constant.ResultEnum;
import com.nest_lot.exceptions.paramExecption;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.service.LogService;
import com.nest_lot.utils.QiniuUtil;
import com.nest_lot.utils.Tools;
import com.qiniu.api.auth.AuthException;

@Controller
@RequestMapping("")
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LogService logService;

	
	
	@RequestMapping("/")
	public String main() {
		if (super.getSubject().isAuthenticated()) {
			return "redirect:/index";
		}
		return "login";
	}

	// 登录
	@RequestMapping(value = "/login")
	public String login() {
		if (super.getSubject().isAuthenticated()) {
			return "redirect:/index";
		}
		return "login";
	}

	// 登录成功
	@RequestMapping(value = "/index")
	public String index(Model model) {
		if (!super.getSubject().isAuthenticated()) {
			return "redirect:/login";
		}
		super.request.setAttribute("userName", super.getSysUserBean().getUserLoginId());
		logService.insertLog(super.getSysUserBean().getUserLoginId(), "登陆", "",Tools.getIpAddress(super.request));
		return "index";
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		try {
			super.getSubject().logout();
			return "redirect:/login";
		} catch (Exception e) {
			logger.error("", e);
			return "redirect:/index";
		}
	}
	
	
	
	/**
	 * ajax登录请求
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
	public ResultBase submitLogin(@RequestParam("userLoginId") String userLoginId,
			@RequestParam("userPwd") String userPwd, @RequestParam("rememberMe") boolean rememberMe) {
		ResultBase result = new ResultBase();
		if (Tools.isTrimEmpty(userLoginId))
			return result.fail(ResultEnum.ERR_1101.getCode(), ResultEnum.ERR_1101.getMessage());
		if (Tools.isTrimEmpty(userPwd))
			return result.fail(ResultEnum.ERR_1102.getCode(), ResultEnum.ERR_1102.getMessage());
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(userLoginId, userPwd, rememberMe);
			super.getSubject().login(token);
			result.success();
		} catch (paramExecption e) {
			logger.error("", e);
			result.fail(e.getErrorCode(), e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param newPwd
	 * @param userPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	public ResultBase updatePwd(@RequestParam("newPwd") String newPwd, @RequestParam("userPwd") String userPwd) {
		ResultBase resultBase = new ResultBase();
		SysUserBean bean = super.getSysUserBean();
		if (!bean.getUserPwd().equals(userPwd)) {
			resultBase.fail(ResultEnum.ERR_1121.getCode(), ResultEnum.ERR_1121.getMessage());
			return resultBase;
		}
		ResultBase base = super.sysUserInfoService.updatePwd(newPwd, bean.getUserLoginId());
		return base;
	}


	// 被踢出后跳转的页面
	@RequestMapping(value = "/kickout")
	public String kickout() {
		return "/error/kickout";
	}

	

	/**
	 * @param request
	 * @param multipartFile
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResultBase qiniuUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile multipartFile) throws JSONException {
		ResultBase resultBase = new ResultBase();
		QiniuUtil qiniuUtil = new QiniuUtil();
		try {
			/**
			 * 上传文件扩展名
			 */
			String filenameExtension = multipartFile.getOriginalFilename().substring(
					multipartFile.getOriginalFilename().lastIndexOf("."), multipartFile.getOriginalFilename().length());

			/**
			 * MultipartFile 转 file 类型
			 */
			File file = Tools.multipartToFile(multipartFile);

			/**
			 * 七牛云文件上传 服务 file文件 以及 文件扩展名
			 */
			resultBase = qiniuUtil.uploadFile(file, filenameExtension);
			if (!resultBase.isSuccess()) {
				return resultBase;
			}

		} catch (AuthException e) {
			logger.error("AuthException", e);
		}

		return resultBase;
	}

}
