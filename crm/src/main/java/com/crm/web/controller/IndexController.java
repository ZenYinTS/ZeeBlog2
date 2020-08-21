package com.crm.web.controller;

import com.crm.domain.Menu;
import com.crm.service.IMenuService;
import com.crm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/queryForMenu")
    @ResponseBody
    public List<Menu> queryForMenu(){
//        List<Menu> result = menuService.queryForRoot();

        //直接从session中获取
        List<Menu> result = (List<Menu>) UserContext.get().getSession().getAttribute(UserContext.MENUINSESSION);
        return result;
    }
}
