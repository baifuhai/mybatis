package com.test.dao;

import com.test.entity.Emp;
import org.apache.ibatis.annotations.Select;

public interface EmpMapperAnnotation {

    @Select("select * from emp where id = #{id}")
    Emp getById(Integer id);

}
