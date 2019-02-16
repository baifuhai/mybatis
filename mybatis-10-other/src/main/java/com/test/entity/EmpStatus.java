package com.test.entity;

import java.util.HashMap;
import java.util.Map;

public enum EmpStatus {
	LOGIN("100", "用户登录"),
	LOGOUT("200", "用户登出"),
	REMOVE("300", "用户不存在");

	private String code;
	private String msg;

	private EmpStatus(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	private static final Map<String, EmpStatus> map = new HashMap<>();

	static {
		for (EmpStatus state : EmpStatus.values()) {
			map.put(state.getCode(), state);
		}
	}

	//按照状态码返回枚举对象
	public static EmpStatus getByCode(String code) {
		/*EmpStatus[] values = EmpStatus.values();
		for (EmpStatus value : values) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;*/
		return map.get(code);
	}

}
