package com.zeecode.blog.web.admin;

import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import com.zeecode.blog.po.User;
import com.zeecode.blog.service.BlogService;
import com.zeecode.blog.service.TagService;
import com.zeecode.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


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
    public String input(Model model) {
        model.addAttribute("types",typeService.listType());    //初始化分类
        model.addAttribute("tags",tagService.listTag());    //初始化标签
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    //新增时的提交
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //初始化
        //设置当前登录用户
        blog.setUser((User) session.getAttribute("user"));
        //根据用户选择的分类和标签进行初始化
        blog.setType(typeService.getType(blog.getTypeId()));    //根据隐藏域里的type.id
        blog.setTags(tagService.listTag(blog.getTagIds()));    //可以使用session.getAttribute("tagIds")

        Blog b = blogService.saveBlog(blog);
        if(b==null){
            //保存失败
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/blogs";
    }

}
