package com.test;

import com.test.dao.DeptMapper;
import com.test.dao.EmpMapper;
import com.test.entity.Dept;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tests {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private EmpMapper empMapper;
    private DeptMapper deptMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 获取到的 SqlSession 不会自动提交
        session = sqlSessionFactory.openSession();

        empMapper = session.getMapper(EmpMapper.class);
        deptMapper = session.getMapper(DeptMapper.class);
    }

    @After
    public void after() {
        if (session != null) {
            // 手动提交
            session.commit();
            session.close();
        }
    }

    /**
     * 测试增删改
     * 1、mybatis 允许增删改直接定义以下类型返回值
     * 		Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * 		sqlSessionFactory.openSession() 手动提交
     * 		sqlSessionFactory.openSession(true) 自动提交
     */
    @Test
    public void testInsert() {
        Emp emp = new Emp("a", "a@126.com", "m");
        int count = empMapper.insert(emp);
        System.out.println(emp.getId());
        System.out.println(count);
    }

    @Test
    public void testUpdate() {
        Emp emp = new Emp(1, "b", "b@126.com", "f");
        int count = empMapper.update(emp);
        System.out.println(count);
    }

    @Test
    public void testDelete() {
        int count = empMapper.delete(1);
        System.out.println(count);
    }

    @Test
    public void testGetById() {
        Emp emp = empMapper.getById(1);
        System.out.println(emp);
    }

    @Test
    public void testGetByIdAndLastName() {
        Emp emp = empMapper.getByIdAndLastName(1, "a");
        System.out.println(emp);
    }

    @Test
    public void testGetByIdAndLastName2() {
        Emp emp = empMapper.getByIdAndLastName2(1, "a");
        System.out.println(emp);
    }

    @Test
    public void testGetByIdAndLastName3() {
        Emp emp = empMapper.getByIdAndLastName3(new Emp(1, "a", null, null));
        System.out.println(emp);
    }

    @Test
    public void testGetByIdAndLastName4() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("lastName", "a");
        Emp emp = empMapper.getByIdAndLastName4(map);
        System.out.println(emp);
    }

    @Test
    public void testGet01() {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", "emp");
        map.put("id", 1);
        map.put("lastName", "a");
        Emp emp = empMapper.testGet01(map);
        System.out.println(emp);
    }

    @Test
    public void testGetEmps01() {
        List<Emp> emps = empMapper.testGetEmps01(1);
        System.out.println(emps);
    }

    @Test
    public void testGetEmps02() {
        Map<String, Object> emp = empMapper.testGetEmps02(1);
        System.out.println(emp);
    }

    @Test
    public void testGetEmps03() {
        Map<Integer, Emp> emps = empMapper.testGetEmps03(1);
        System.out.println(emps);
    }

    @Test
    public void testResultMap01() {
        Emp emp = empMapper.testResultMap01(1);
        System.out.println(emp);
    }

    @Test
    public void testResultMapAssociation00() {
        Emp emp = empMapper.testResultMapAssociation00(1);
        System.out.println(emp);
    }

    @Test
    public void testResultMapAssociation01() {
        Emp emp = empMapper.testResultMapAssociation01(1);
        System.out.println(emp);
    }

    @Test
    public void testResultMapAssociation02() {
        List<Emp> emps = empMapper.testResultMapAssociation02(1);
        System.out.println(emps.get(0).getId());
        System.out.println(emps.get(0).getDept());
    }

    @Test
    public void testResultMapCollection01() {
        List<Dept> depts = deptMapper.testResultMapCollection01();
        for (Dept dept : depts) {
            System.out.println(dept);
        }
    }

    @Test
    public void testResultMapCollection02() {
        List<Dept> depts = deptMapper.testResultMapCollection02();
        for (Dept dept : depts) {
            System.out.println(dept);
        }
    }

    @Test
    public void testResultMapDiscriminator() {
        List<Emp> emps = empMapper.testResultMapDiscriminator();
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

}
