package com.zeecode.blog.service;

import com.zeecode.blog.NotFoundException;
import com.zeecode.blog.dao.TagRepository;
import com.zeecode.blog.po.Blog;
import com.zeecode.blog.po.Tag;
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
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tag> listTag(String ids) {//根据Long类型的list查找标签
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        List<Tag> tags = this.listTag();
        List<Tag> tagTop = new ArrayList<>();
        //过滤，去掉未发布的博客
        for (Tag tag:tags){
            Tag temTag =tag;
            List<Blog> temBlog = new ArrayList<>();
            for (Blog b : tag.getBlogs()){
                if (b.isPublished())
                    temBlog.add(b);
            }
            temTag.setBlogs(temBlog);
            tagTop.add(temTag);
        }
        Collections.sort(tagTop,(t1, t2)->{
            return t2.getBlogs().size()-t1.getBlogs().size();
        });
        if (size>=tagTop.size())
            return tagTop;
        else
            return tagTop.subList(0,size);
    }

    //将1，2,3,字符串转为Long类型的list
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if (ids!=null&&!ids.isEmpty()){
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                try {
                    Tag tag = tagRepository.findTagById(new Long(idArray[i]));
                    if (tag==null){ //说明标签不存在
                        throw new NumberFormatException();
                    }
                    list.add(new Long(idArray[i]));
                }catch (NumberFormatException e){    //说明标签不存在
                    Tag newTag = new Tag();
                    newTag.setName(idArray[i]);
                    tagRepository.save(newTag);
                    list.add(tagRepository.findByName(idArray[i]).getId());
                }
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if (t == null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
