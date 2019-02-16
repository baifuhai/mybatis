package com.test;

import com.test.dao.EmpMapper;
import com.test.entity.Emp;
import com.test.entity.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tests {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMbg() throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream inputStream = Resources.getResourceAsStream("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void testMyBatis3Simple() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);
            List<Emp> list = empMapper.selectByExample(null);
            for (Emp emp : list) {
                System.out.println(emp);
            }
        } finally {
            session.close();
        }
    }

    @Test
    public void testMyBatis3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            //查询员工名字中有a的，和员工性别是m，或email有a的

            //xxxExample 就是封装查询条件的
            EmpExample example = new EmpExample();

            //创建一个Criteria，这个Criteria就是拼装查询条件
            EmpExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%a%");
            criteria.andGenderEqualTo("m");

            EmpExample.Criteria criteria2 = example.or();
            criteria2.andEmailLike("%a%");

            List<Emp> list = mapper.selectByExample(example);
            for (Emp emp : list) {
                System.out.println(emp);
            }
        } finally {
            session.close();
        }
    }

}
