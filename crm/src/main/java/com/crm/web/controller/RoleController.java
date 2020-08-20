package com.crm.web.controller;

import com.crm.domain.PageResult;
import com.crm.domain.Role;
import com.crm.query.RoleQueryObject;
import com.crm.service.IRoleService;
import com.crm.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/role")
    public String list(){
        return "role";
    }

    @ResponseBody
    @RequestMapping("/role_save")
    public AjaxResult save(Role role){
        AjaxResult result = null;
        try {
            roleService.insert(role);
            result = new AjaxResult(true,"保存成功！");
        } catch (Exception e) {
            result = new AjaxResult("保存失败，请联系管理员！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/role_list")
    public PageResult roleList(RoleQueryObject qo){
        PageResult result = null;
        result = roleService.queryForPage(qo);
        return result;
    }

    @ResponseBody
    @RequestMapping("role_update")
    public AjaxResult update(Role role){
        AjaxResult result = null;
        try {
            roleService.updateByPrimaryKey(role);
            result = new AjaxResult(true,"更新成功！");
        }catch (Exception e){
            result = new AjaxResult("更新失败，请联系管理员！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/role_remove")
    public AjaxResult remove(Long id){
        AjaxResult result = null;
        try {
            roleService.deleteByPrimaryKey(id);
            result = new AjaxResult(true,"删除成功！");
        } catch (Exception e) {
            result = new AjaxResult("删除失败，请联系管理员！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/roles_queryForEmp")
    public List<Role> queryForEmp(){
        List<Role> result = null;
        result = roleService.selectAll();
        return result;
    }

    @ResponseBody
    @RequestMapping("/role_queryByEid")
    public List<Long> queryByEid(Long eid){
        List<Long> result = null;
        result = roleService.queryByEid(eid);
        return result;
    }
}
