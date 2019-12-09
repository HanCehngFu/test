package com.nest_lot.model;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotNull;

public class CustomMessage {
	private String title;// 标题
	private String description;// 描述
	private String type;// 类型(消息需要跳转时使用)
	private JSONObject extend;// 扩展字段

	public CustomMessage() {
		this.title = "";
		this.description = "";
		this.type = "";
		this.extend = new JSONObject();
	}

	public static CustomMessage newBuilder() {
		return new CustomMessage();
	}

	public CustomMessage setTitle(String title) {
		this.title = title;
		return this;
	}

	public CustomMessage setDescription(String description) {
		this.description = description;
		return this;
	}

	public CustomMessage setType(String type) {
		this.type = type;
		return this;
	}

	public CustomMessage setExtends(JSONObject extend) {
		this.extend = extend;
		return this;
	}

	public CustomMessage addExtend(@NotNull String key, String value) {
		this.extend.put(key, value);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	public JSONObject getExtend() {
		return extend;
	}

	public String build() {
		return JSONObject.toJSONString(this);
	}

	@Override
	public String toString() {
		return build();
	}

	public CustomMessage getInstance() {
		return this;
	}
}
