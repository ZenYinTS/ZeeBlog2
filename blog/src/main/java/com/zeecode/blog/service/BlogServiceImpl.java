package com.zeecode.blog.service;

import com.zeecode.blog.NotFoundException;
import com.zeecode.blog.dao.BlogRepository;
import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import com.zeecode.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        //动态查询
        //CriteriaQuery存放查询条件的容器
        //CriteriaBuilder存放查询条件的表达式，如相等，模糊查询like
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //组合条件放在list中
                List<Predicate> predicates = new ArrayList<>();
                if (blogQuery.getTitle()!=null && !blogQuery.getTitle().trim().isEmpty()){    //查询的标题非空
                    //构造like查询表达式，存放在list中，第一个参数是属性的名字，第二个参数是属性的值
                    //相当于where title like '%我的%';
                    predicates.add(cb.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if (blogQuery.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blogQuery.getTypeId()));
                }
                //点了推荐才去构建查询条件
                if (blogQuery.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blogQuery.isRecommend()));
                }
                //构造查询条件的where子句，需要传入数组
                //集合转为数组toArray，转为对象数组需要传入对象数组参数，并指定大小
                cq.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;//不用管
            }
        },pageable);    //第一个参数为Specification实现动态查询，第二个参数为pageable实现分页
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b==null){
            throw new NotFoundException("该博客不存在！");
        }
        BeanUtils.copyProperties(blog,b);
        return  blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
