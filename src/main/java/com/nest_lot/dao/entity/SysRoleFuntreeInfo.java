package com.nest_lot.dao.entity;

public class SysRoleFuntreeInfo {

	private Long rfId;

	private String rfUuid = "";

	private String roleUuid = "";

	private String funUuid = "";

	public Long getRfId() {
		return rfId;
	}

	public void setRfId(Long rfId) {
		this.rfId = rfId;
	}

	public String getRfUuid() {
		return rfUuid;
	}

	public void setRfUuid(String rfUuid) {
		this.rfUuid = rfUuid == null ? null : rfUuid.trim();
	}

	public String getRoleUuid() {
		return roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid == null ? null : roleUuid.trim();
	}

	public String getFunUuid() {
		return funUuid;
	}

	public void setFunUuid(String funUuid) {
		this.funUuid = funUuid == null ? null : funUuid.trim();
	}
}