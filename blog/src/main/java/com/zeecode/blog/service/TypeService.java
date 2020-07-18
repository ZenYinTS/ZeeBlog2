package com.zeecode.blog.service;

import com.zeecode.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);    //新增
    Type getType(Long id);
    Type getTypeByName(String name);    //根据名称查找分类
    Page<Type> listType(Pageable pageable);    //分页查询
    List<Type> listType();
    Type updateType(Long id,Type type);
    void deleteType(Long id);
}
