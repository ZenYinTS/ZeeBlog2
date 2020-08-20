package com.crm.service.impl;

import com.crm.domain.Employee;
import com.crm.service.IEmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEmployeeImpl {

    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void test(){
        employeeService.insert(new Employee());    //测试是否成功插入
    }

}
