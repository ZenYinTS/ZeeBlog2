package com.zeecode.blog.service;

import com.zeecode.blog.NotFoundException;
import com.zeecode.blog.dao.TypeRepository;
import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        List<Type> types = this.listType();
        List<Type> typeTop = new ArrayList<>();
        //过滤，去掉未发布的博客
        for (Type type:types){
            Type temType =type;
            List<Blog> temBlog = new ArrayList<>();
            for (Blog b : type.getBlogs()){
                if (b.isPublished())
                    temBlog.add(b);
            }
            temType.setBlogs(temBlog);
            typeTop.add(temType);
        }
        Collections.sort(typeTop,(t1,t2)->{
            return t2.getBlogs().size()-t1.getBlogs().size();
        });
        if (size>=typeTop.size())
            return typeTop;
        else
            return typeTop.subList(0,size);
    }


    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
