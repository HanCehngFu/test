package com.nest_lot.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.nest_lot.base.BaseDao;
import com.nest_lot.dao.entity.SysUserInfo;

public interface SysUserInfoMapper extends BaseDao<SysUserInfo> {

	// 登陆验证
	SysUserInfo checkUserLoginId(@Param("userLoginId") String id, @Param("userPwd") String usePwd);

	// 修改密码
	int updatePwd(@Param("userPwd") String pwd, @Param("userPwdMd5") String userPwdMd5, @Param("userLoginId") String userLoginId);

	// 通过loginId查找用户
	SysUserInfo queryByLoginId(@Param("userLoginId") String loginId);

}