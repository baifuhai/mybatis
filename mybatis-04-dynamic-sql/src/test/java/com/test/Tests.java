package com.test;

import com.test.dao.EmpMapper;
import com.test.entity.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private EmpMapper empMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
        empMapper = session.getMapper(EmpMapper.class);
    }

    @After
    public void after() {
        if (session != null) {
            session.commit();
            session.close();
        }
    }

    @Test
    public void testIf() {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setLastName("a");
        emp.setEmail("a@126.com");
        emp.setGender("m");

        List<Emp> emps = empMapper.testIf(emp);
        for (Emp e : emps) {
            System.out.println(e);
        }
    }

    @Test
    public void testTrim() {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setLastName("a");
        emp.setEmail("a@126.com");
        emp.setGender("m");

        List<Emp> emps = empMapper.testTrim(emp);
        for (Emp e : emps) {
            System.out.println(e);
        }
    }

    @Test
    public void testChoose() {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setLastName("a");
        emp.setEmail("a@126.com");
        emp.setGender("m");

        List<Emp> emps = empMapper.testChoose(emp);
        for (Emp e : emps) {
            System.out.println(e);
        }
    }

    @Test
    public void testSet() {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setLastName("b");
        emp.setEmail("b@126.com");
        emp.setGender("f");
        empMapper.testSet(emp);
    }

    @Test
    public void testForeach() {
        List<Emp> emps = empMapper.testForeach(Arrays.asList(1, 2));
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

    @Test
    public void testMysqlInsertBatch() {
        List<Emp> empList = new ArrayList<>();
        empList.add(new Emp("a", "a@126.com", "m", 1));
        empList.add(new Emp("b", "b@126.com", "f", 2));
        empList.add(new Emp("c", "c@126.com", "m", 1));
        empMapper.testMysqlInsertBatch(empList);
    }

    @Test
    public void testMysqlInsertBatch2() {
        List<Emp> empList = new ArrayList<>();
        empList.add(new Emp("a", "a@126.com", "m", 1));
        empList.add(new Emp("b", "b@126.com", "f", 2));
        empList.add(new Emp("c", "c@126.com", "m", 1));
        empMapper.testMysqlInsertBatch2(empList);
    }

    @Test
    public void testOracleInsertBatch() {
        List<Emp> empList = new ArrayList<>();
        empList.add(new Emp("a", "a@126.com", "m", 1));
        empList.add(new Emp("b", "b@126.com", "f", 2));
        empList.add(new Emp("c", "c@126.com", "m", 1));
        empMapper.testOracleInsertBatch(empList);
    }

    @Test
    public void testOracleInsertBatch2() {
        List<Emp> empList = new ArrayList<>();
        empList.add(new Emp("a", "a@126.com", "m", 1));
        empList.add(new Emp("b", "b@126.com", "f", 2));
        empList.add(new Emp("c", "c@126.com", "m", 1));
        empMapper.testOracleInsertBatch2(empList);
    }

    @Test
    public void testInnerParameter() {
        Emp emp = new Emp();
        emp.setId(1);
        emp = null;

        List<Emp> empList = empMapper.testInnerParameter(emp);
        for (Emp e : empList) {
            System.out.println(e);
        }
    }

    @Test
    public void testBind() {
        Emp emp = new Emp();
        emp.setLastName("a");

        List<Emp> empList = empMapper.testBind(emp);
        for (Emp e : empList) {
            System.out.println(e);
        }
    }

    @Test
    public void testSqlInclude() {
        List<Emp> empList = empMapper.testSqlInclude();
        for (Emp e : empList) {
            System.out.println(e);
        }
    }

}
