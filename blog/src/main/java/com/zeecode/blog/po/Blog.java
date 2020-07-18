package com.zeecode.blog.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Lob
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)    //此注解是因为在数据库中存放的时间格式只有日期，需要用这个注解转为日期+时间
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //Blog与Type是多对一的关系，所以Blog类中有一个Type对象
    @ManyToOne
    private Type type;

    //Blog与Tag是多对多的关系，所以Blog类中有多个Tag对象
    //级联新增，博客新增一个tag后，相应的tag表中也新增该tag
    // 级联持久化（保存）操作（持久保存拥有方实体时，也会持久保存该实体的所有相关数据。）给当前设置的实体操作另一个实体的权限
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();
}
