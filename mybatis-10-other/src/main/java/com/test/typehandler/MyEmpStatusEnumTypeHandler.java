package com.test.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.entity.EmpStatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 实现TypeHandler接口，或者继承 BaseTypeHandler
 */
public class MyEmpStatusEnumTypeHandler implements TypeHandler<EmpStatus> {

	/**
	 * 定义当前数据如何保存到数据库中
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
		System.out.println("要保存的状态码：" + parameter.getCode());
		ps.setString(i, parameter.getCode());
	}

	@Override
	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
		//需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
		String code = rs.getString(columnName);
		System.out.println("从数据库中获取的状态码：" + code);
		EmpStatus status = EmpStatus.getByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		String code = rs.getString(columnIndex);
		System.out.println("从数据库中获取的状态码：" + code);
		EmpStatus status = EmpStatus.getByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String code = cs.getString(columnIndex);
		System.out.println("从数据库中获取的状态码：" + code);
		EmpStatus status = EmpStatus.getByCode(code);
		return status;
	}

}
