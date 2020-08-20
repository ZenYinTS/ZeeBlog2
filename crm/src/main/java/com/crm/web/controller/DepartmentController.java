package com.crm.web.controller;

import com.crm.domain.Department;
import com.crm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/dept_queryForEmp")
    //要求的返回数据是对象数组的json格式，则返回List，再使用@ResponseBody进行转化
    public List<Department> queryForEmp(){
        List result = null;
        result = departmentService.queryForEmp();
        return result;
    }
}
