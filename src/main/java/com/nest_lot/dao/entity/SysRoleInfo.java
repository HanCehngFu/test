package com.nest_lot.dao.entity;

public class SysRoleInfo {
	private Long roleId;

	private String roleUuid = "";

	private String roleName = "";

	private String roleTime = "";

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleUuid() {
		return roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid == null ? null : roleUuid.trim();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public String getRoleTime() {
		return roleTime;
	}

	public void setRoleTime(String roleTime) {
		this.roleTime = roleTime == null ? null : roleTime.trim();
	}
}