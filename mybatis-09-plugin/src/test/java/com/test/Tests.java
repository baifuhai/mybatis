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
     * 插件原理：
     * 在四大对象创建的时候
     * 1、每个创建出来的对象不是直接返回的，而是 interceptorChain.pluginAll(parameterHandler);
     * 2、获取到所有的 Interceptor（拦截器）（插件需要实现的接口）
     * 		调用 interceptor.plugin(target); 返回 target 包装后的对象
     * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象
     *
     * 插件编写：
     * 1、编写 Interceptor 的实现类
     * 2、使用 @Intercepts 注解完成插件签名
     * 3、将写好的插件注册到全局配置文件中
     */
    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);
            Emp emp = empMapper.getById(1);
            System.out.println(emp);
        } finally {
            session.close();
        }
    }

}
