package com.zeecode.blog.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="分类名称不能为空！")
    private String name;

    //Type与Blog是一对多的关系，所以Type类中有多个Blog对象
    //type是被维护关系，blog是主动维护type，jpa规定多的一端为维护端
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

}
