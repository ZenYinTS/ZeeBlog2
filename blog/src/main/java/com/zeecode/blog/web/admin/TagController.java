package com.zeecode.blog.web.admin;

import com.zeecode.blog.po.Tag;
import com.zeecode.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size =5,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        //设置分页参数为10条，按照id排序，倒序排序
        //model为前端模型，设置参数后，前端可以拿到这个值
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")    //跳转方式一个是get一个是post，不会发生冲突
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","不能添加同名标签！");    //第一个参数要与类中的一致
        }

        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t==null){
            //保存失败
            attributes.addFlashAttribute("message","新增失败！");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")    //跳转方式一个是get一个是post，不会发生冲突
    //使用@PathVariable将路径中的id传入，否则无法传入id
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1!=null){    //说明存在同名分类
            //自定义返回结果，将结果返回到前端
            result.rejectValue("name","nameError","已有同名标签，更新失败！");    //第一个参数要与类中的一致
        }

        if(result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id,tag);
        if(t==null){
            //保存失败
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tags";
    }
}
