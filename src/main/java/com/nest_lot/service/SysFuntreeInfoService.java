package com.nest_lot.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nest_lot.base.BaseService;
import com.nest_lot.dao.entity.SysFuntreeInfo;
import com.nest_lot.dao.mapper.SysFuntreeInfoMapper;

@Service
public class SysFuntreeInfoService extends BaseService<SysFuntreeInfo> {

	@Autowired
	private SysFuntreeInfoMapper funtreeInfoMapper;

	/**
	 * 多个ID查询多条记录
	 * 
	 * @param id
	 * @return
	 */
	public List<SysFuntreeInfo> selectByFunList(String[] id) {
		List<SysFuntreeInfo> list = funtreeInfoMapper.selectByFunList(id);
		return list;
	}

	/**
	 * PID查询多条记录
	 * 
	 * @param id
	 * @return
	 */
	public List<SysFuntreeInfo> selectByPidList(String Pid) {
		List<SysFuntreeInfo> list = funtreeInfoMapper.selectByPidList(Pid);
		return list;
	}

	/**
	 * 条件查询
	 * 
	 * @param name
	 * @return
	 */
	public List<SysFuntreeInfo> queryByListNoPid(String name) {
		return funtreeInfoMapper.queryByListNoPid(name);
	}

	/**
	 * 权限组
	 * 
	 * @param name
	 * @return
	 */
	public List<SysFuntreeInfo> queryByPidList() {
		return funtreeInfoMapper.queryByPidList();
	}

}
