package com.zeecode.blog.service;

import com.zeecode.blog.pojo.Comment;;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
