package com.zeecode.blog.web;

import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import com.zeecode.blog.po.Type;
import com.zeecode.blog.service.BlogService;
import com.zeecode.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        List<Type> types = typeService.listTypeTop(1000);
        //从首页点进来的不存在id，即id为-1
        if (id==-1){
            id=types.get(0).getId();
        }
        model.addAttribute("types",types);

        BlogQuery bq = new BlogQuery();
        bq.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,bq));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
