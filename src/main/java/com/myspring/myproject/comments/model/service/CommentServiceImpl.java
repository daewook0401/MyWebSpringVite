package com.myspring.myproject.comments.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.auth.service.AuthService;
import com.myspring.myproject.board.model.service.BoardService;
import com.myspring.myproject.comments.model.dao.CommentMapper;
import com.myspring.myproject.comments.model.dto.CommentDTO;
import com.myspring.myproject.comments.model.vo.Comment;
import com.myspring.myproject.exception.InvalidUserREquestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final BoardService boardService;
    private final AuthService authService;

    @Override
    public void insertComment(CommentDTO comment) {
        
        // 게시글 번호가 존재하는지 확인
        boardService.findById(comment.getBoardId());

        // 요청한 사용자랑 토큰 소유주가 같은지 확인
        Long tokenUserId= ((CustomUserDetails)authService.getUserDetails()).getUserId();

        if (tokenUserId != comment.getCommentWriter()){
            throw new InvalidUserREquestException("유효하지 않음");
        }
        Comment requestData = Comment.builder()
                                    .commentWriter(tokenUserId)
                                    .commentContent(comment.getCommentContent())
                                    .boardId(comment.getBoardId())
                                    .build();
        commentMapper.insertComment(requestData);

    }

    @Override
    public List<CommentDTO> selectCommentList(Long boardId) {
        boardService.findById(boardId);
        return commentMapper.selectCommentList(boardId);
    }

    @Override
    public void deleteById(Long commentId) {
        commentMapper.deleteById(commentId);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO comment) {
        commentMapper.updateComment(comment);
        return comment;
    }

}
