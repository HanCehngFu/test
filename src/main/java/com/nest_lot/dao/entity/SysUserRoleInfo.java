package com.nest_lot.dao.entity;

public class SysUserRoleInfo {
	private Long urId;

	private String urUuid = "";

	private String userUuid = "";

	private String userName = "";

	private String roleUuid = "";

	private String roleName = "";

	public Long getUrId() {
		return urId;
	}

	public void setUrId(Long urId) {
		this.urId = urId;
	}

	public String getUrUuid() {
		return urUuid;
	}

	public void setUrUuid(String urUuid) {
		this.urUuid = urUuid == null ? null : urUuid.trim();
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid == null ? null : userUuid.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
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
}