package com.myspring.myproject.comments.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myspring.myproject.comments.model.dto.CommentDTO;
import com.myspring.myproject.comments.model.vo.Comment;

@Mapper
public interface CommentMapper {

    void insertComment(Comment comment);

    List<CommentDTO> selectCommentList(Long boardNo);

    void deleteById(Long commentId);

    void updateComment(CommentDTO comment);
}
