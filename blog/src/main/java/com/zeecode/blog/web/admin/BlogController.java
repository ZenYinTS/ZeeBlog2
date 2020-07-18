package com.zeecode.blog.web.admin;

import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import com.zeecode.blog.service.BlogService;
import com.zeecode.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
            BlogQuery blogQuery,
                        Model model) {
        //在刚进入博客页面时，加载好分类列表
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/blogs";
    }

    //实现部分渲染，只需要进行表格的渲染，无需更改搜索条件，所以不用渲染更改条件
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blogQuery,
                         Model model) {
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input() {
        return "admin/blogs-input";
    }


}
