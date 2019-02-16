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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session1;
    private SqlSession session2;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session1 = sqlSessionFactory.openSession();
        session2 = sqlSessionFactory.openSession();
    }

    /**
     * 两级缓存
     *
     * 一级缓存：本地缓存，sqlSession 级别的缓存。一级缓存是一直开启的，必须使用，无法关闭
     * 		同一个 sqlSession 查询到的数据会放在自己的缓存中
     * 		以后如果需要获取相同的数据，直接从缓存中拿，不会再去查询数据库
     * 		如果两次查询之间执行了增删改操作（清除一级二级缓存）或调用 sqlSession 的 clearCache（清除一级缓存），则还会查数据库
     *
     * 二级缓存：全局缓存，namespace 级别的缓存。一个 namespace 对应一个二级缓存
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中
     * 		2、如果会话提交或者关闭，一级缓存中的数据会被保存到二级缓存中，新的会话查询同样的数据，就从二级缓存中的拿
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去 mapper.xml 中配置使用二级缓存：<cache></cache>
     * 			3）、我们的 POJO 需要实现序列化接口，如果 readOnly 是 false
     *
     * 和缓存有关的设置/属性：
     * 		1）、全局配置 cacheEnabled：是否开启二级缓存
     * 		2）、每个 select 标签都有 useCache 属性，是否使用二级缓存，默认 true；也有 flushCache 属性，默认是 false
     * 		3）、每个 增删改 标签都有 flushCache 属性，是否清除一级二级缓存，增删改执行完成后清除，默认 true
     * 		4）、sqlSession.clearCache(); 只清除当前 session 的一级缓存
     * 		5）、全局配置 localCacheScope 本地缓存（一级缓存）作用域
     * 				默认是 SESSION，如果配置成 STATEMENT，可以禁用一级缓存
     *
     * 第三方缓存整合：
     *		1）、导入第三方缓存包
     *		2）、导入与第三方缓存整合的适配包，mybatis 官方有
     *		3）、mapper.xml 使用 cache 的 type 属性指定缓存全类名
     *
     */
    @Test
    public void testFirstLevelCache01() {
        try {
            EmpMapper empMapper = session1.getMapper(EmpMapper.class);

            Emp emp1 = empMapper.getById(1);
            Emp emp2 = empMapper.getById(1);

            System.out.println(emp1 == emp2);//true
        } finally {
            session1.close();
        }
    }

    @Test
    public void testFirstLevelCache02() {
        try {
            EmpMapper empMapper = session1.getMapper(EmpMapper.class);

            Emp emp1 = empMapper.getById(1);

            emp1.setId(2);

            Emp emp2 = empMapper.getById(1);

            //一级缓存貌似没法设置readOnly
            System.out.println(emp1);//id=2
            System.out.println(emp2);//id=2
            System.out.println(emp1 == emp2);//true
        } finally {
            session1.close();
        }
    }

    @Test
    public void testFirstLevelCache03() {
        try {
            EmpMapper empMapper = session1.getMapper(EmpMapper.class);

            Emp emp1 = empMapper.getById(1);

            //empMapper.insert(new Emp("aa", "aa@126.com", "m", 1));

            //清除该 session 的缓存
            session1.clearCache();

            Emp emp2 = empMapper.getById(1);

            System.out.println(emp1 == emp2);
        } finally {
            session1.commit();
            session1.close();
        }
    }

    @Test
    public void testFirstLevelCache04() {
        try {
            EmpMapper empMapper1 = session1.getMapper(EmpMapper.class);
            EmpMapper empMapper2 = session2.getMapper(EmpMapper.class);

            Emp emp1 = empMapper1.getById(1);
            Emp emp2 = empMapper2.getById(1);

            System.out.println(emp1 == emp2);
        } finally {
            session1.close();
            session2.close();
        }
    }

    @Test
    public void testSecondLevelCache01() {
        try {
            EmpMapper empMapper1 = session1.getMapper(EmpMapper.class);
            EmpMapper empMapper2 = session2.getMapper(EmpMapper.class);

            Emp emp1 = empMapper1.getById(1);

            session1.close();

            //增删改会清除一级二级缓存
            empMapper2.insert(new Emp("bb", "bb@126.com", "m", 1));

            //从二级缓存中拿到的数据，并没有发送新的 sql
            Emp emp2 = empMapper2.getById(1);

            System.out.println(emp1 == emp2);//如果 cache 标签的 readOnly 是 true，则相等
        } finally {
            session1.close();
            session2.close();
        }
    }

    @Test
    public void testSecondLevelCache02() {
        try {
            DeptMapper deptMapper1 = session1.getMapper(DeptMapper.class);
            DeptMapper deptMapper2 = session2.getMapper(DeptMapper.class);

            Dept dept1 = deptMapper1.getById(1);

            session1.close();

            Dept dept2 = deptMapper2.getById(1);

            System.out.println(dept1 == dept2);
        } finally {
            session1.close();
            session2.close();
        }
    }

}
