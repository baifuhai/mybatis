package com.test.service;

import com.test.dao.EmpMapper;
import com.test.entity.Emp;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    EmpMapper empMapper;

    @Autowired
    SqlSession sqlSessionBatch;

    public List<Emp> getList() {
        return empMapper.getList();
    }

    public void insertBatch() {
        EmpMapper empMapperBatch = sqlSessionBatch.getMapper(EmpMapper.class);
        for (int i = 0; i < 10000; i++) {
            empMapperBatch.insert(new Emp("a", "a@126.com", "m", 1));
        }
    }

}
