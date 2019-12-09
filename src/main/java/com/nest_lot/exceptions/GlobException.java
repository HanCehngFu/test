package com.nest_lot.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nest_lot.constant.ResultBase;

@ControllerAdvice
public class GlobException {
	@ExceptionHandler(value = { paramExecption.class })
	@ResponseBody
	public ResultBase toErrorHtml(paramExecption e) {
		ResultBase resultBase = new ResultBase();
		resultBase.setCode(e.getErrorCode());
		resultBase.setMessage(e.getMessage());
		return resultBase;
	}
}
