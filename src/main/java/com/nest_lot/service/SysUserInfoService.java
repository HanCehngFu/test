package com.nest_lot.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nest_lot.base.BaseService;
import com.nest_lot.constant.ResultBase;
import com.nest_lot.constant.ResultEnum;
import com.nest_lot.dao.entity.SysUserInfo;
import com.nest_lot.dao.entity.SysUserRoleInfo;
import com.nest_lot.dao.mapper.SysUserInfoMapper;
import com.nest_lot.exceptions.paramExecption;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.utils.SecurityUtil;
import com.nest_lot.utils.Tools;

@Service
public class SysUserInfoService extends BaseService<SysUserInfo> {

	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
	@Autowired
	private SysUserRoleInfoService sysUserRoleInfoService;

	@Autowired
	private LogService logService;

	/**
	 * 登陆查询 shiro做逻辑判断
	 * 
	 * @param userLoginId
	 * @return
	 */
	public SysUserInfo checkUserLoginId(String userLoginId, String userPwd) {
		SysUserInfo sysUserInfo = sysUserInfoMapper.checkUserLoginId(userLoginId, userPwd);
		return sysUserInfo;
	}

	/**
	 * 修改密码
	 * 
	 * @param userPwd
	 * @param userLoginId
	 * @return
	 */
	@Transactional
	public ResultBase updatePwd(String userPwd, String userLoginId) {
		ResultBase resultBase = new ResultBase();
		String userPwdMd5 = SecurityUtil.encodemd5(userPwd);
		int result = sysUserInfoMapper.updatePwd(userPwd, userPwdMd5, userLoginId);
		if (result < 1) {
			throw new paramExecption(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
		}
		return resultBase.success();
	}

	/**
	 * 修改添加
	 * 
	 * @param sysUserInfo
	 * @param roleName
	 * @param roleUuid
	 * @param urUuid
	 * @return
	 */
	@Transactional
	public ResultBase add(SysUserInfo sysUserInfo, String roleName, String roleUuid, String urUuid, SysUserBean sysUserBean, HttpServletRequest request) {
		ResultBase resultBase = new ResultBase();
		String user_login_id = sysUserInfo.getUserLoginId();
		if (Tools.isEmpty(user_login_id)) {
			return resultBase.fail(ResultEnum.ERR_1101.getCode(), ResultEnum.ERR_1101.getMessage());
		}
		// 不管是添加还是修改 roleName,roleUuid和UserName都可能不一样
		SysUserRoleInfo surRole = new SysUserRoleInfo();
		surRole.setRoleName(roleName);
		surRole.setRoleUuid(roleUuid);
		surRole.setUserName(sysUserInfo.getUserName());
		SysUserInfo info = queryByLogingId(user_login_id);
		if (Tools.isEmpty(sysUserInfo.getUserUuid())) {
			if (info != null) {
				return resultBase.fail(ResultEnum.ERR_1117.getCode(), ResultEnum.ERR_1117.getMessage());
			}
			sysUserInfo.setUserPwdMd5(SecurityUtil.encodemd5(sysUserInfo.getUserPwd()));
			sysUserInfo.setUserUuid(Tools.getUUID());
			sysUserInfo.setUserTime(Tools.timeStamp2Date());
			sysUserInfo.setUserStatus("1");
			sysUserInfo.setTenant(0);
			surRole.setUserUuid((sysUserInfo.getUserUuid()));
			surRole.setUrUuid(Tools.getUUID());
			int result = sysUserInfoMapper.insert(sysUserInfo);
			int resultRole = sysUserRoleInfoService.insert(surRole);
			if (result < 1 || resultRole < 1) {
				throw new paramExecption(ResultEnum.ERR_1116.getCode(), ResultEnum.ERR_1116.getMessage());
			}
			logService.insertLog(sysUserBean.getUserLoginId(), "用户管理新增", sysUserInfo.getUserUuid(), Tools.getIpAddress(request));
		} else {
			if (info != null && (!info.getUserUuid().equals(sysUserInfo.getUserUuid()))) {
				return resultBase.fail(ResultEnum.ERR_1117.getCode(), ResultEnum.ERR_1117.getMessage());
			}
			sysUserInfo.setUserPwdMd5(SecurityUtil.encodemd5(sysUserInfo.getUserPwd()));
			surRole.setUrUuid(urUuid);
			surRole.setUserUuid(sysUserInfo.getUserUuid());
			int result = update(sysUserInfo);
			int resultRole = sysUserRoleInfoService.update(surRole);
			if (result < 1 || resultRole < 1) {
				throw new paramExecption(ResultEnum.ERR_1119.getCode(), ResultEnum.ERR_1119.getMessage());
			}
			logService.insertLog(sysUserBean.getUserLoginId(), "用户管理修改", sysUserInfo.getUserUuid(), Tools.getIpAddress(request));
		}
		return resultBase.success();
	}

	/**
	 * 去重
	 * 
	 * @param UserLoginId
	 * @return
	 */
	public SysUserInfo queryByLogingId(String UserLoginId) {
		return sysUserInfoMapper.queryByLoginId(UserLoginId);
	}
}
