package com.zeecode.blog.web.admin;

import com.zeecode.blog.po.Type;
import com.zeecode.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size =5,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        //设置分页参数为10条，按照id排序，倒序排序
        //model为前端模型，设置参数后，前端可以拿到这个值
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")    //跳转方式一个是get一个是post，不会发生冲突
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){    //说明存在同名分类
            //自定义返回结果，将结果返回到前端,注意Type后一定要是BindingResult，否则BindingResult没有效果
            result.rejectValue("name","nameError","不能添加同名分类！");    //第一个参数要与类中的一致
        }

        //@Valid注解表示要校验type对象，BindingResult用于接收校验后的结果
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t==null){
            //保存失败
            attributes.addFlashAttribute("message","新增失败！");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")    //跳转方式一个是get一个是post，不会发生冲突
    //使用@PathVariable将路径中的id传入，否则无法传入id
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1!=null){    //说明存在同名分类
            //自定义返回结果，将结果返回到前端
            result.rejectValue("name","nameError","已有同名分类，更新失败！");    //第一个参数要与类中的一致
        }

        //@Valid注解表示要校验type对象，BindingResult用于接收校验后的结果
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t==null){
            //保存失败
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }
}
