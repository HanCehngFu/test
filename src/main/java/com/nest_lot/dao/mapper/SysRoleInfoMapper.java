package com.nest_lot.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nest_lot.base.BaseDao;
import com.nest_lot.dao.entity.SysRoleInfo;

public interface SysRoleInfoMapper extends BaseDao<SysRoleInfo> {

	List<SysRoleInfo> selectByRoleList(@Param("RoleListId") String[] RoleListId);

	List<SysRoleInfo> queryByList(@Param("name") String roleName);
}