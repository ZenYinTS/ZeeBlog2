package com.zeecode.blog.service;

import com.zeecode.blog.dao.CommentRepository;
import com.zeecode.blog.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        //获取所有父节点
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId!=-1){    //回复评论，评论为子评论
            //获取到父评论，设置为当前评论的父评论，建立上下级关系
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环复制每个顶级评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment:comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);    //避免对原始数据造成修改，导致数据在数据库中发生变化
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中（即子孙同级存放）
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment:comments){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                //循环迭代，找出子代，放入temReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(temReplys);
            //清除临时存放区
            temReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> temReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @paranm comment  被迭代对象
     * @return
     */
    private void recursively(Comment comment){
        temReplys.add(comment);    //先将传入的子代节点存入子代集合中
        if (comment.getReplyComments().size()>0){
            //存放子代的子代
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply:replys){
                temReplys.add(reply);
                //若子代存在子代，则递归继续遍历
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
