package com.nest_lot.constant;

import java.io.Serializable;

/**
 * 接口返回结果
 */
@SuppressWarnings("serial")
public class ResultBase implements Serializable {
	private static final int SUCCESS_CODE = 200;
	private static final String SUCCESS_MESSAGE = "操作成功";

	private int code;

	private String message;

	private Object entity;

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultBase success() {
		this.code = SUCCESS_CODE;
		this.message = SUCCESS_MESSAGE;
		return this;
	}

	public ResultBase success(Object entity) {
		this.code = SUCCESS_CODE;
		this.message = SUCCESS_MESSAGE;
		this.entity = entity;
		return this;
	}

	public ResultBase fail(int code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}

	public boolean isSuccess() {
		if (this.code == SUCCESS_CODE)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return String.format("result->{code: [%s], message: [%s]}", this.getCode(), this.getMessage());
	}
}