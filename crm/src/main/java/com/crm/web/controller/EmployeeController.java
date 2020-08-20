package com.crm.web.controller;

import com.crm.domain.Employee;
import com.crm.domain.PageResult;
import com.crm.domain.Role;
import com.crm.query.EmployeeQueryObject;
import com.crm.service.IEmployeeService;
import com.crm.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;


    @RequestMapping(value="/employee")
    public String list(){
        return "employee";
    }

    @ResponseBody
    @RequestMapping(value="/employee_list")
    public PageResult employeeList(EmployeeQueryObject queryObject){
        PageResult result = null;
        //根据请求的字段获取到分页信息
        result = employeeService.queryForPage(queryObject);
        return result;
    }

    @ResponseBody
    @RequestMapping("/employee_save")
    public AjaxResult save(Employee employee){
        AjaxResult result = null;
        try {
            //employee不在表单中的字段赋值
            employee.setPassword("888888");
            employee.setAdmin(false);
            employee.setState(true);
            //插入
            employeeService.insert(employee);
            result = new AjaxResult(true,"保存成功！");
        }catch (Exception e){
            result = new AjaxResult("保存异常，请联系管理员！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/employee_update")
    public AjaxResult update(Employee employee){
        AjaxResult result = null;
        try {
            //更新
            employeeService.updateByPrimaryKey(employee);
            result = new AjaxResult(true,"更新成功！");
        }catch (Exception e){
            result = new AjaxResult("更新异常，请联系管理员！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("employee_remove")
    public AjaxResult remove(Long id){
        AjaxResult result = null;
        try {
            //删除操作采用软删除，只是修改在职状态
            employeeService.updateState(id);
            result = new AjaxResult(true,"离职成功！");
        }catch (Exception e){
            result = new AjaxResult("离职失败，请联系管理员！");
        }
        return result;
    }
}


