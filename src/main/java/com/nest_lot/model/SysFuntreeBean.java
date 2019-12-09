package com.nest_lot.model;

/**
 * 权限管理关联权限组bean
 * 
 * @author 吴榧
 *
 */
public class SysFuntreeBean {

	private Long funId;

	private String funUuid;

	private String funName;

	private String funLevel;

	private String funAddr;

	private String funOrder;

	private String funPid;

	private String pidName;

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
		this.funUuid = funUuid;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getFunLevel() {
		return funLevel;
	}

	public void setFunLevel(String funLevel) {
		this.funLevel = funLevel;
	}

	public String getFunAddr() {
		return funAddr;
	}

	public void setFunAddr(String funAddr) {
		this.funAddr = funAddr;
	}

	public String getFunOrder() {
		return funOrder;
	}

	public void setFunOrder(String funOrder) {
		this.funOrder = funOrder;
	}

	public String getFunPid() {
		return funPid;
	}

	public void setFunPid(String funPid) {
		this.funPid = funPid;
	}

	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}

}
