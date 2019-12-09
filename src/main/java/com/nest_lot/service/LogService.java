package com.nest_lot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nest_lot.base.BaseService;
import com.nest_lot.dao.entity.LogInfo;
import com.nest_lot.utils.Tools;

@Service
public class LogService extends BaseService<LogInfo> {

	@Transactional
	public int insertLog(String name, String content, String Id, String ip) {
		LogInfo entity = new LogInfo();
		if (content.equals("登陆")) {
			content = "登陆";
		} else {
			content = name + "对" + content + "操作,操作表单uuid为" + Id;
		}

		entity.setCreateTime(Tools.timeStamp2Date());
		entity.setLogContent(content);
		entity.setUserName(name);
		entity.setIp(ip);
		return insert(entity);
	}

}
