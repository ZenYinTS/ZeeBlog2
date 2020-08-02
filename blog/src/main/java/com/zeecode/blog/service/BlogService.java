package com.zeecode.blog.service;

import com.zeecode.blog.pojo.Blog;
import com.zeecode.blog.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery,boolean flag);

    Page<Blog> listPublishedBlogPageable(Pageable pageable);

    Page<Blog> listPublishedBlog(Pageable pageable, Long tagId);

    Page<Blog> listPublishedBlog(Pageable pageable,String query);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listPublishedBlog();

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
