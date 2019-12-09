package com.nest_lot.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nest_lot.base.BaseDao;
import com.nest_lot.dao.entity.SysFuntreeInfo;

public interface SysFuntreeInfoMapper extends BaseDao<SysFuntreeInfo> {

	List<SysFuntreeInfo> selectByFunList(@Param("FunListId") String[] funListId);

	List<SysFuntreeInfo> selectByPidList(@Param("funPid") String pid);

	List<SysFuntreeInfo> queryByListNoPid(@Param("funName") String name);

	List<SysFuntreeInfo> queryByPidList();

}