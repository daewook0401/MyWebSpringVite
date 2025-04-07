package com.myspring.myproject.comments.model.service;

import java.util.List;

import com.myspring.myproject.comments.model.dto.CommentDTO;

public interface CommentService {
    void insertComment(CommentDTO comment);
    void deleteById(Long commentId);
    List<CommentDTO> selectCommentList(Long boardId);
    CommentDTO updateComment(Long commentId, CommentDTO comment);
}
