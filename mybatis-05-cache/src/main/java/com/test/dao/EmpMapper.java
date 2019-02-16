package com.test.dao;

import com.test.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    Emp getById(Integer id);

    int insert(Emp emp);

}
