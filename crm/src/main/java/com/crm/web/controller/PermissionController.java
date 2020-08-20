package com.crm.web.controller;

import com.crm.domain.PageResult;
import com.crm.query.PermissionQueryObject;
import com.crm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @ResponseBody
    @RequestMapping("/permission_list")
    public PageResult list(PermissionQueryObject qo){
        PageResult result = null;
        result = permissionService.queryForPage(qo);
        return result;
    }

    @ResponseBody
    @RequestMapping("/permissions_queryByRid")
    public PageResult permissionsQueryByRid(PermissionQueryObject qo){
        //请求中携带了参数rid，在PermissionQueryObject中添加rid字段获取参数
        PageResult result = null;
        result = permissionService.queryForPage(qo);
        return result;
    }
}
