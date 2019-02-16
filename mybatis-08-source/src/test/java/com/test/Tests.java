package com.test;

import com.test.dao.EmpMapper;
import com.test.entity.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Tests {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、获取 sqlSessionFactory 对象:
     * 		解析文件的每一个信息保存在 Configuration 中，返回包含 Configuration 的 DefaultSqlSession
     * 		注意：MappedStatement 代表一个增删改查的详细信息
     *
     * 2、获取 sqlSession 对象
     * 		返回一个 DefaultSqlSession 对象，包含 Executor 和 Configuration
     * 		这一步会创建 Executor 对象
     *
     * 3、获取接口的代理对象（MapperProxy）
     * 		DefaultSqlSession.getMapper(Class)，使用 MapperProxyFactory 创建一个 MapperProxy 的代理对象
     * 		代理对象里面包含了，DefaultSqlSession（Executor）
     *
     * 4、执行增删改查方法
     *
     * 总结：
     * 	1、根据配置文件（全局，sql映射）初始化出 Configuration 对象
     * 	2、创建一个 DefaultSqlSession 对象，他里面包含 Configuration 以及 Executor（根据全局配置文件中的 defaultExecutorType 创建出对应的 Executor）
     *  3、DefaultSqlSession.getMapper(Class)：拿到 Mapper 接口对应的 MapperProxy
     *  4、MapperProxy 里面有 DefaultSqlSession
     *  5、执行增删改查方法：
     *  		1）、调用 DefaultSqlSession 的增删改查（Executor）；
     *  		2）、会创建一个 StatementHandler 对象，同时也会创建出 ParameterHandler 和 ResultSetHandler
     *  		3）、调用 StatementHandler 预编译参数以及设置参数值，使用 ParameterHandler 来给 sql 设置参数
     *  		4）、调用 StatementHandler 的增删改查方法
     *  		5）、使用 ResultSetHandler 封装结果
     *  注意：
     *  	四大对象每个创建的时候都有一个 interceptorChain.pluginAll(parameterHandler);
     *
     */
    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);
            Emp emp = empMapper.getById(1);
            System.out.println(empMapper);
            System.out.println(emp);
        } finally {
            session.close();
        }
    }

}
