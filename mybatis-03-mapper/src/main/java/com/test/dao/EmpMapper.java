package com.test.dao;

import com.test.entity.Emp;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmpMapper {

    int insert(Emp emp);

    int update(Emp emp);

    int delete(Integer id);

    Emp getById(Integer id);

    Emp getByIdAndLastName(Integer id, String lastName);

    Emp getByIdAndLastName2(@Param("id") Integer id, @Param("lastName") String lastName);

    Emp getByIdAndLastName3(Emp emp);

    Emp getByIdAndLastName4(Map<String, Object> map);

    Emp testGet01(Map<String, Object> map);

    List<Emp> testGetEmps01(Integer id);

    // 返回一条记录的 map，key 是列名，value 是值
    Map<String, Object> testGetEmps02(Integer id);

    // 多条记录封装一个 map，key 是这条记录的主键，value 是记录封装后的 javaBean
    // @MapKey 告诉 mybatis 封装这个 map 的时候使用哪个属性作为key
    @MapKey("id")
    Map<Integer, Emp> testGetEmps03(Integer id);

    Emp testResultMap01(Integer id);

    Emp testResultMapAssociation00(Integer id);

    Emp testResultMapAssociation01(Integer id);

    List<Emp> testResultMapAssociation02(Integer id);

    List<Emp> getByDeptId(Integer deptId);

    List<Emp> testResultMapDiscriminator();
}
