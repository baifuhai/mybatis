package com.test.dao;

import com.test.entity.Emp;
import com.test.entity.OraclePage;

import java.util.List;

public interface EmpMapper {

    List<Emp> getList();

    void insert(Emp emp);

    void getPageByProcedure(OraclePage page);

    Emp getById(Integer id);

}