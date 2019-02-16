package com.test.dao;

import com.test.entity.Dept;

import java.util.List;

public interface DeptMapper {

    Dept getById(Integer id);

    List<Dept> testResultMapCollection01();

    List<Dept> testResultMapCollection02();

}
