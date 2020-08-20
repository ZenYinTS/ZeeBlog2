package com.crm.web.controller;

import com.crm.util.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
