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

/**
 * 配置步骤：
 * 1、创建全局配置文件（包含数据库连接池信息，事务管理器信息等）
 * 2、创建 sql 映射文件（包含sql语句的映射信息）
 * 3、将 sql 映射文件注册在全局配置文件中
 *
 * 代码步骤：
 * 1、根据全局配置文件创建一个 SqlSessionFactory 对象
 * 2、使用 SqlSessionFactory 对象获取 SqlSession 对象，使用他来执行增删改查，一个 SqlSession 代表和数据库的一次会话，用完要关闭
 * 3、使用 sql 的唯一标志来告诉 mybatis 执行哪个 sql，或使用 Mapper 执行方法
 *
 * 接口式编程
 *  原来：		Dao		====>  DaoImpl
 *  mybatis：	Mapper	====>  xxMapper.xml
 *
 * mapper 接口没有实现类，但是 mybatis 会为这个接口生成一个代理对象
 *
 * SqlSession 和 connection 一样她都是非线程安全，每次使用都应该去获取新的对象
 *
 */
public class Tests {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        // 获取SqlSessionFactory对象
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
            // 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
            session = sqlSessionFactory.openSession();

            Emp emp = session.selectOne("com.test.dao.EmpMapper.getById", 1);

            System.out.println(emp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Test
    public void test02() {
        SqlSession session = null;
        try {
            // 获取 SqlSession 对象
            session = sqlSessionFactory.openSession();

            // 获取接口的实现类对象，mybatis 会为接口自动的创建一个代理对象，代理对象去执行方法
            EmpMapper empMapper = session.getMapper(EmpMapper.class);
            Emp emp = empMapper.getById(1);

            System.out.println(empMapper.getClass());
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
