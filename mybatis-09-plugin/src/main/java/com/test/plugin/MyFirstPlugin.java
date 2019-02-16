package com.test.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * 插件签名：
 *		告诉 MyBatis 当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
    @Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)
})
public class MyFirstPlugin implements Interceptor {

    /**
     * 拦截目标对象的目标方法的执行
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        System.out.println("MyFirstPlugin intercept... method: " + invocation.getMethod());
        if ("prepare".equals(methodName)) {
            //prepare(invocation);
        } else if ("parameterize".equals(methodName)) {
            parameterize(invocation);
        }
        //执行目标方法
        return invocation.proceed();
    }

    private void prepare(Invocation invocation) {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        System.out.println("parameterObject: " + boundSql.getParameterObject());

        MetaObject boundSqlMetaObject = SystemMetaObject.forObject(boundSql);
        boundSqlMetaObject.setValue("parameterObject", 2);

        System.out.println("parameterObject: " + boundSql.getParameterObject());
    }

    public void parameterize(Invocation invocation) {
        Object target = invocation.getTarget();//org.apache.ibatis.executor.statement.RoutingStatementHandler

        //动态的改变一下 sql 运行的参数：本来是查询1号员工，实际查询2号员工
        //拿到 StatementHandler -> ParameterHandler -> parameterObject

        //拿到 target 的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);

        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是：" + value);

        //修改 sql 语句要用的参数
        metaObject.setValue("parameterHandler.parameterObject", 2);
    }

    /**
     * 包装目标对象的，为目标对象创建一个代理对象
     */
    @Override
    public Object plugin(Object target) {
        //我们可以借助 Plugin 的 wrap 方法来使用当前 Interceptor 包装我们目标对象
        System.out.println("MyFirstPlugin plugin... target: " + target);
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    public Object plugin2(Object target) {
        //应该不用判断
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * 将插件注册时的 property 属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息：" + properties);
    }

}
