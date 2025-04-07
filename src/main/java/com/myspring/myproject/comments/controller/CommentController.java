package com.myspring.myproject.comments.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myspring.myproject.board.model.dto.BoardDTO;
import com.myspring.myproject.comments.model.dto.CommentDTO;
import com.myspring.myproject.comments.model.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    
    @PostMapping
    public ResponseEntity<?> insertComment(@Valid @RequestBody CommentDTO comment){
        commentService.insertComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping()
    public ResponseEntity<List<CommentDTO>> selectCommentList(@RequestParam(name="boardId") Long request){
        return ResponseEntity.ok(commentService.selectCommentList(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name="id") Long commentId){
        commentService.deleteById(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable(name="id") Long commentId, @RequestBody CommentDTO comment){
        comment.setCommentId(commentId);
        log.info("--------------------------------------------{}", comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.updateComment(commentId,comment));
    }

}