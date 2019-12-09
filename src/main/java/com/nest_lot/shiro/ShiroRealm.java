package com.nest_lot.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.nest_lot.constant.ResultEnum;
import com.nest_lot.dao.entity.SysFuntreeInfo;
import com.nest_lot.dao.entity.SysRoleFuntreeInfo;
import com.nest_lot.dao.entity.SysRoleInfo;
import com.nest_lot.dao.entity.SysUserInfo;
import com.nest_lot.dao.entity.SysUserRoleInfo;
import com.nest_lot.exceptions.paramExecption;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.service.SysFuntreeInfoService;
import com.nest_lot.service.SysRoleFuntreeInfoService;
import com.nest_lot.service.SysRoleInfoServcie;
import com.nest_lot.service.SysUserInfoService;
import com.nest_lot.service.SysUserRoleInfoService;
import com.nest_lot.utils.Tools;

/**
 * 功能：shiro身份校验核心类 作者：wf 日期：2018年6月18日
 */

public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private SysUserRoleInfoService sysUserRoleInfoService;
	@Autowired
	private SysRoleInfoServcie sysRoleInfoServcie;
	@Autowired
	private SysFuntreeInfoService sysFuntreeInfoService;
	@Autowired
	private SysRoleFuntreeInfoService sysRoleFuntreeInfoService;
	@Autowired
	private SysUserInfoService sysUserInfoService;

	/**
	 * 认证信息(身份验证)
	 */
	@Override
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws paramExecption {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String pw = String.valueOf(token.getPassword());
		SysUserInfo sysUserInfo = sysUserInfoService.checkUserLoginId(username, pw);
		if (sysUserInfo == null)
			throw new paramExecption(ResultEnum.ERR_1105.getCode(), ResultEnum.ERR_1105.getMessage());
		if (!sysUserInfo.getUserStatus().equals("1"))
			throw new paramExecption(ResultEnum.ERR_1112.getCode(), ResultEnum.ERR_1112.getMessage());
		SysUserRoleInfo sysUserRoleInfo = sysUserRoleInfoService.queryById(sysUserInfo.getUserUuid());
		if (sysUserRoleInfo == null)
			throw new paramExecption(ResultEnum.ERR_1113.getCode(), ResultEnum.ERR_1113.getMessage());
		SysUserBean sysUserBean = new SysUserBean();
		sysUserBean.setUserId(sysUserInfo.getUserId());
		sysUserBean.setUserLoginId(sysUserInfo.getUserLoginId());
		sysUserBean.setUserName(sysUserInfo.getUserName());
		sysUserBean.setUserPwd(sysUserInfo.getUserPwd());
		sysUserBean.setUserUuid(sysUserInfo.getUserUuid());
		sysUserBean.setRoleName(sysUserRoleInfo.getRoleName());
		sysUserBean.setRoleUuid(sysUserRoleInfo.getRoleUuid());
		sysUserBean.setTenant(sysUserInfo.getTenant());
		return new SimpleAuthenticationInfo(sysUserBean, pw.toCharArray(), getName());
	}

	/**
	 * 授权
	 */

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Object key = principals.getPrimaryPrincipal();
		SysUserBean sysUserRoleInfo = new SysUserBean();
		try {
			BeanUtils.copyProperties(sysUserRoleInfo, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String roleUuid = sysUserRoleInfo.getRoleUuid();
		if (Tools.isEmpty(roleUuid)) {
			return info;
		}
		List<SysFuntreeInfo> sysFuntreeInfos = new ArrayList<>();
		if (!roleUuid.equals("*")) {
			SysRoleInfo sysRoleInfo = sysRoleInfoServcie.queryById(roleUuid);
			if (sysRoleInfo == null)
				return info;
			List<SysRoleFuntreeInfo> sysRoleFuntreeInfos = (List<SysRoleFuntreeInfo>) sysRoleFuntreeInfoService.queryByListId(roleUuid);
			if (sysRoleFuntreeInfos == null || sysRoleFuntreeInfos.isEmpty())
				return info;

			String[] functionIds = new String[sysRoleFuntreeInfos.size()];
			for (int i = 0; i < sysRoleFuntreeInfos.size(); i++)
				functionIds[i] = sysRoleFuntreeInfos.get(i).getFunUuid();
			sysFuntreeInfos = sysFuntreeInfoService.selectByFunList(functionIds);

			if (functionIds == null || CollectionUtils.isEmpty(sysFuntreeInfos))
				return info;
		} else {
			sysFuntreeInfos = sysFuntreeInfoService.queryByListNoPid("");
		}
		// 权限sign
		Set<String> stringPermissions = new HashSet<String>();
		for (SysFuntreeInfo function : sysFuntreeInfos)
			stringPermissions.add(function.getFunOrder());
		info.setStringPermissions(stringPermissions);
		return info;
	}

}
