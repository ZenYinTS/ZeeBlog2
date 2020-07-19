package com.zeecode.blog.dao;

import com.zeecode.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//JpaSpecificationExecutor<Blog>接口实现动态组合查询
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.digest like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);
}
