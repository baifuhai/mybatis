package com.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.dao.EmpMapper;
import com.test.entity.Emp;
import com.test.entity.EmpStatus;
import com.test.entity.OraclePage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Tests {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testPageHelper() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);

            Page<Object> page = PageHelper.startPage(3, 2);

            List<Emp> empList = empMapper.getList();

            for (Emp emp : empList) {
                System.out.println(emp);
            }

            System.out.println("当前页码：" + page.getPageNum());
            System.out.println("每页的记录数：" + page.getPageSize());
            System.out.println("总记录数：" + page.getTotal());
            System.out.println("总页码：" + page.getPages());

            //navigatePages 要连续显示多少页
            PageInfo<Emp> info = new PageInfo<>(empList, 3);

            System.out.println();
            System.out.println("当前页码：" + info.getPageNum());
            System.out.println("每页的记录数："+info.getPageSize());
            System.out.println("总记录数：" + info.getTotal());
            System.out.println("总页码：" + info.getPages());
            System.out.println("是否第一页：" + info.isIsFirstPage());
            System.out.println("连续显示的页码：");
            int[] nums = info.getNavigatepageNums();
            for (int i = 0; i < nums.length; i++) {
                System.out.println(nums[i]);
            }
        } finally {
            session.close();
        }
    }

    /**
     * 批量：预编译 sql 1 次，设置参数 10000 次， 执行 1 次
     * 非批量：（预编译 sql，设置参数，执行）* 10000 次
     *
     * 1、批量操作是在 session.commit() 以后才发送 sql 语句给数据库进行执行的
     * 2、如果我们想让其提前执行，以方便后续可能的查询操作获取数据，我们可以使用 sqlSession.flushStatements() 方法，让其直接冲刷到数据库进行执行
     */
    @Test
    public void testBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //可以执行批量操作的 sqlSession
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);

        long start = System.currentTimeMillis();

        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);

            for (int i = 0; i < 10000; i++) {
                if (i == 5000) {
                    session.flushStatements();
                }
                empMapper.insert(new Emp("a", "a@126.com", "m", 1, EmpStatus.LOGIN));
            }

            session.commit();

            long end = System.currentTimeMillis();

            System.out.println("time: " + (end - start) + " ms");
        } finally {
            session.close();
        }
    }

    @Test
    public void testProcedure() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = session.getMapper(EmpMapper.class);

            OraclePage page = new OraclePage();
            page.setStart(1);
            page.setEnd(5);

            empMapper.getPageByProcedure(page);

            System.out.println("总记录数：" + page.getCount());
            System.out.println("查出的数据：" + page.getEmps());
        } finally {
            session.close();
        }
    }

    @Test
    public void testEnumUse(){
        EmpStatus login = EmpStatus.LOGIN;
        System.out.println("枚举的索引：" + login.ordinal());
        System.out.println("枚举的名字：" + login.name());
        System.out.println("枚举的状态码：" + login.getCode());
        System.out.println("枚举的提示消息：" + login.getMsg());
    }

    /**
     * 默认 mybatis 在处理枚举对象的时候保存的是枚举的名字 EnumTypeHandler
     *
     * EnumTypeHandler        使用枚举的名字 name()
     * EnumOrdinalTypeHandler 使用枚举的索引 ordinal()
     */
    @Test
    public void testEnum() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmpMapper empMapper = openSession.getMapper(EmpMapper.class);

            Emp emp = new Emp("a", "a@126.com", "m", 1, EmpStatus.LOGIN);

            empMapper.insert(emp);

            Emp empById = empMapper.getById(emp.getId());
            System.out.println(empById);
        } finally {
            openSession.commit();
            openSession.close();
        }
    }

}
