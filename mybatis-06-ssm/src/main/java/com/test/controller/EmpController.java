package com.test.controller;

import com.test.entity.Emp;
import com.test.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("emp")
@Controller
public class EmpController {

    @Autowired
    EmpService empService;

    @RequestMapping("getList")
    public String getList(Map<String,Object> map){
        List<Emp> empList = empService.getList();
        map.put("empList", empList);
        return "empList";
    }

    @RequestMapping("insertBatch")
    @ResponseBody
    public String insertBatch(){
        empService.insertBatch();
        return "success";
    }

}
