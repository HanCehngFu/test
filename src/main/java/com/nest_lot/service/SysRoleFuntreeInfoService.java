package com.nest_lot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nest_lot.base.BaseService;
import com.nest_lot.dao.entity.SysRoleFuntreeInfo;
import com.nest_lot.dao.mapper.SysRoleFuntreeInfoMapper;

@Service
public class SysRoleFuntreeInfoService extends BaseService<SysRoleFuntreeInfo> {

	@Autowired
	private SysRoleFuntreeInfoMapper funtreeInfoMapper;

	public List<SysRoleFuntreeInfo> selectByRoleList(String roleUuid) {
		return funtreeInfoMapper.selectByRoleList(roleUuid);
	}
}
