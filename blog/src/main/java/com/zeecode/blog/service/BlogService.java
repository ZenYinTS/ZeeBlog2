package com.zeecode.blog.service;

import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getBlogByTitle(String title);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);
}
