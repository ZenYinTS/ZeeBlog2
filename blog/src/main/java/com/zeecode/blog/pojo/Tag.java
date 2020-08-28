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
@Table(name="t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message="标签名称不能为空！")
    private String name;

    //Blog与Tag是多对多的关系，所以Blog类中有多个Tag对象
    //指定tags为被维护关系
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
