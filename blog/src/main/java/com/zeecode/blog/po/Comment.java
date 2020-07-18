package com.zeecode.blog.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String avator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    //自关联关系（一个父对象对应多个子对象）
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;    //可回复的相邻的父类只有一个
}
