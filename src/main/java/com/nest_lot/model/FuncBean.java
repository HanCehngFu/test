package com.nest_lot.model;

import java.util.List;
import com.nest_lot.dao.entity.SysFuntreeInfo;

/**
 * 角色授权时候用户页面回填
 * 
 * @author 吴榧
 *
 */
public class FuncBean {
	private String funUuPid;

	private String funPname;

	private List<SysFuntreeInfo> funtreeInfos;

	public String getFunUuPid() {
		return funUuPid;
	}

	public void setFunUuPid(String funUuPid) {
		this.funUuPid = funUuPid;
	}

	public String getFunPname() {
		return funPname;
	}

	public void setFunPname(String funPname) {
		this.funPname = funPname;
	}

	public List<SysFuntreeInfo> getFuntreeInfos() {
		return funtreeInfos;
	}

	public void setFuntreeInfos(List<SysFuntreeInfo> funtreeInfos) {
		this.funtreeInfos = funtreeInfos;
	}

}
