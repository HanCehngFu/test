package com.nest_lot.dao.entity;

public class SysFuntreeInfo {
	private Long funId;

	private String funUuid = "";

	private String funName = "";

	private String funLevel = "";

	private String funAddr = "";

	private String funOrder = "";

	private String funPid = "";

	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public String getFunUuid() {
		return funUuid;
	}

	public void setFunUuid(String funUuid) {
		this.funUuid = funUuid == null ? null : funUuid.trim();
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName == null ? null : funName.trim();
	}

	public String getFunLevel() {
		return funLevel;
	}

	public void setFunLevel(String funLevel) {
		this.funLevel = funLevel == null ? null : funLevel.trim();
	}

	public String getFunAddr() {
		return funAddr;
	}

	public void setFunAddr(String funAddr) {
		this.funAddr = funAddr == null ? null : funAddr.trim();
	}

	public String getFunOrder() {
		return funOrder;
	}

	public void setFunOrder(String funOrder) {
		this.funOrder = funOrder == null ? null : funOrder.trim();
	}

	public String getFunPid() {
		return funPid;
	}

	public void setFunPid(String funPid) {
		this.funPid = funPid == null ? null : funPid.trim();
	}
}