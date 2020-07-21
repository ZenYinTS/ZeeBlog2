package com.zeecode.blog.service;

import com.zeecode.blog.NotFoundException;
import com.zeecode.blog.dao.BlogRepository;
import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.BlogQuery;
import com.zeecode.blog.po.Type;
import com.zeecode.blog.util.MarkdownUtils;
import com.zeecode.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

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
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery,boolean flag) {
        //动态查询
        //CriteriaQuery存放查询条件的容器
        //CriteriaBuilder存放查询条件的表达式，如相等，模糊查询like
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //组合条件放在list中
                List<Predicate> predicates = new ArrayList<>();
                if (blogQuery.getTitle() != null && !blogQuery.getTitle().trim().isEmpty()) {    //查询的标题非空
                    //构造like查询表达式，存放在list中，第一个参数是属性的名字，第二个参数是属性的值
                    //相当于where title like '%我的%';
                    predicates.add(cb.like(root.<String>get("title"), "%" + blogQuery.getTitle() + "%"));
                }
                if (blogQuery.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                }
                //点了推荐才去构建查询条件
                if (blogQuery.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                //若在前端，只展示发布博客，在后端全部展示
                if (!flag){
                    predicates.add(cb.equal(root.<Boolean>get("published"), true));
                }
                //构造查询条件的where子句，需要传入数组
                //集合转为数组toArray，转为对象数组需要传入对象数组参数，并指定大小
                cq.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;//不用管
            }
        }, pageable);    //第一个参数为Specification实现动态查询，第二个参数为pageable实现分页
    }

    @Transactional
    @Override
    public Page<Blog> listPublishedBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //组合条件放在list中
                List<Predicate> predicates = new ArrayList<>();
                //关联查询，当前的blog关联另外一个tags属性
                Join join = root.join("tags");
                predicates.add(cb.equal(join.get("id"),tagId));
                predicates.add(cb.equal(root.get("published"),true));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listPublishedBlogPageable(Pageable pageable) {
        return blogRepository.listPublishedBlogPageable(pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listPublishedBlog(Pageable pageable, String query) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //初始化
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setDigest("");
        blog.setFlag("原创");
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在！");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());    //编辑的只需要修改更新时间
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year:years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public List<Blog> listPublishedBlog() {
        return blogRepository.listPublishedBlog();
    }
}
