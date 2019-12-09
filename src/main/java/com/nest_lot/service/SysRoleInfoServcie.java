package com.nest_lot.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nest_lot.base.BaseService;
import com.nest_lot.constant.ResultBase;
import com.nest_lot.constant.ResultEnum;
import com.nest_lot.dao.entity.SysRoleInfo;
import com.nest_lot.dao.mapper.SysRoleInfoMapper;
import com.nest_lot.exceptions.paramExecption;
import com.nest_lot.model.SysRoleBean;
import com.nest_lot.model.SysUserBean;
import com.nest_lot.utils.Tools;

@Service
public class SysRoleInfoServcie extends BaseService<SysRoleInfo> {

	

	@Autowired
	private SysRoleInfoMapper sysRoleInfoMapper;

	@Autowired
	private LogService logService;

	/*
	 * @Transactional public ResultBase addOrUpdate(SysRoleBean sysRoleBean,
	 * SysUserBean bean, HttpServletRequest request) { ResultBase base = new
	 * ResultBase(); SysRoleInfo info = new SysRoleInfo(); DepartmentRoleInfo
	 * departmentRoleInfo = new DepartmentRoleInfo(); // 通过中间表获取部门下的角色 判断角色是否添加
	 * List<DepartmentRoleInfo> departmentRoleInfos =
	 * departMentMapper.queryByDepRoleList(sysRoleBean.getDepartmentUuid());
	 * 
	 * String[] roleIds = new String[departmentRoleInfos.size()]; for (int i = 0; i
	 * < departmentRoleInfos.size(); i++) roleIds[i] =
	 * departmentRoleInfos.get(i).getRoleUuid();
	 * 
	 * List<SysRoleInfo> sysRoleInfos = new ArrayList<>(); if (roleIds.length > 0) {
	 * sysRoleInfos = sysRoleInfoMapper.selectByRoleList(roleIds); } //
	 * 通过ID判断是添加还是修改 if (Tools.isEmpty(sysRoleBean.getRoleUuid())) { for
	 * (SysRoleInfo item : sysRoleInfos) { if
	 * (item.getRoleName().equals(sysRoleBean.getRoleName())) { return
	 * base.fail(ResultEnum.ERR_1117.getCode(), ResultEnum.ERR_1117.getMessage()); }
	 * } info.setRoleName(sysRoleBean.getRoleName());
	 * info.setRoleTime(Tools.timeStamp2Date()); info.setRoleUuid(Tools.getUUID());
	 * departmentRoleInfo.setDepartmentUuid(sysRoleBean.getDepartmentUuid());
	 * departmentRoleInfo.setDrStatus("1");
	 * departmentRoleInfo.setRoleUuid(info.getRoleUuid()); int dpResult =
	 * departMentMapper.insert(departmentRoleInfo); int syRole =
	 * sysRoleInfoMapper.insert(info); if (dpResult < 1 || syRole < 1) { throw new
	 * paramExecption(ResultEnum.ERR_1116.getCode(),
	 * ResultEnum.ERR_1116.getMessage()); }
	 * logService.insertLog(bean.getUserLoginId(), "角色管理新增", info.getRoleUuid(),
	 * Tools.getIpAddress(request)); base.success(); } else { for (SysRoleInfo item
	 * : sysRoleInfos) { if (item.getRoleName().equals(sysRoleBean.getRoleName()) &&
	 * (!item.getRoleUuid().equals(sysRoleBean.getRoleUuid()))) { return
	 * base.fail(ResultEnum.ERR_1117.getCode(), ResultEnum.ERR_1117.getMessage()); }
	 * } info.setRoleName(sysRoleBean.getRoleName());
	 * info.setRoleUuid(sysRoleBean.getRoleUuid());
	 * departmentRoleInfo.setRoleUuid(sysRoleBean.getRoleUuid());
	 * departmentRoleInfo.setDepartmentUuid(sysRoleBean.getDepartmentUuid());
	 * departmentRoleInfo.setDrId(sysRoleBean.getDrId()); int dpResult =
	 * departMentMapper.update(departmentRoleInfo); int syRole =
	 * sysRoleInfoMapper.update(info); if (dpResult < 1 || syRole < 1) { throw new
	 * paramExecption(ResultEnum.ERR_1116.getCode(),
	 * ResultEnum.ERR_1116.getMessage()); }
	 * logService.insertLog(bean.getUserLoginId(), "角色管理修改",
	 * sysRoleBean.getRoleUuid(), Tools.getIpAddress(request)); base.success(); }
	 * 
	 * return base;
	 * 
	 * }
	 */

	/**
	 * 查询部门
	 * 
	 * @param name
	 * @return
	 */
	public List<SysRoleInfo> queryByList(String name) {
		return sysRoleInfoMapper.queryByList(name);
	}

}
