package com.nest_lot.exceptions;

public class paramExecption extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorCode = 1001;
	private String message = "操作失败";

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public paramExecption(int errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

}
