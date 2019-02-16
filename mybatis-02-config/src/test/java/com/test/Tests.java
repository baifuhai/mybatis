package com.test;

import com.test.dao.EmpMapperAnnotation;
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

public class Tests {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @After
    public void after() {
    }

    @Test
    public void test01() {
        SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();

            EmpMapperAnnotation empMapperAnnotation = session.getMapper(EmpMapperAnnotation.class);

            Emp emp = empMapperAnnotation.getById(1);

            System.out.println(emp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
