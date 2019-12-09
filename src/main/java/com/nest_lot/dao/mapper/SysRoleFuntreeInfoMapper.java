package com.nest_lot.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nest_lot.base.BaseDao;
import com.nest_lot.dao.entity.SysRoleFuntreeInfo;

public interface SysRoleFuntreeInfoMapper extends BaseDao<SysRoleFuntreeInfo> {

	List<SysRoleFuntreeInfo> selectByRoleList(@Param("uuId") String roleUuid);

}