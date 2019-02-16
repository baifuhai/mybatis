package com.test.dao;

import com.test.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    // 携带了哪个字段查询条件就带上这个字段的值
    List<Emp> testIf(Emp emp);

    List<Emp> testTrim(Emp emp);

    List<Emp> testChoose(Emp emp);

    void testSet(Emp emp);

    List<Emp> testForeach(@Param("idList") List<Integer> idList);

    void testMysqlInsertBatch(@Param("empList") List<Emp> empList);

    void testMysqlInsertBatch2(@Param("empList") List<Emp> empList);

    void testOracleInsertBatch(@Param("empList") List<Emp> empList);

    void testOracleInsertBatch2(@Param("empList") List<Emp> empList);

    List<Emp> testInnerParameter(Emp emp);

    List<Emp> testBind(Emp emp);

    List<Emp> testSqlInclude();

}
