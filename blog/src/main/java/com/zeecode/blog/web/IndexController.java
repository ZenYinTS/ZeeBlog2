package com.zeecode.blog.web;

import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.Tag;
import com.zeecode.blog.po.Type;
import com.zeecode.blog.service.BlogService;
import com.zeecode.blog.service.TagService;
import com.zeecode.blog.service.TypeService;
import com.zeecode.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        //前端列表展示所需数据
        model.addAttribute("page",blogService.listPublishedBlogPageable(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(5));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listPublishedBlog(pageable,("%"+query+"%")));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/search")
    public String search1(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                          HttpServletRequest request, Model model){
        String q = request.getParameter("q");
        model.addAttribute("page",blogService.listPublishedBlog(pageable,("%"+q+"%")));
        model.addAttribute("query",q);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
    //关于我
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
