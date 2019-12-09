package com.nest_lot.model;

/**
 * 角色页面数据回填和保存时候bean
 * 
 * @author 吴榧
 *
 */
public class SysRoleBean {

	private Long roleId;

	private String roleUuid;

	private String roleName;

	private String roleTime;

	private String departmentUuid;

	private String departmentName;

	private Long drId;

	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

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
		this.roleUuid = roleUuid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleTime() {
		return roleTime;
	}

	public void setRoleTime(String roleTime) {
		this.roleTime = roleTime;
	}

	public String getDepartmentUuid() {
		return departmentUuid;
	}

	public void setDepartmentUuid(String departmentUuid) {
		this.departmentUuid = departmentUuid;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
