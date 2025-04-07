package com.myspring.myproject.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.board.model.dto.BoardDTO;
import com.myspring.myproject.board.model.service.BoardService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> save(@Valid BoardDTO board, @RequestParam(name="file", required=false) MultipartFile file){
        // log.info("게시글 정보 : {}, 파일 정보 : {}", board, file.getOriginalFilename());
        boardService.save(board, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<BoardDTO>> getBoardsByCategory(
            @PathVariable("id") Long categoryId,                             // ① PathVariable
            @RequestParam(name = "limit", defaultValue = "10")
            @Min(value = 1, message = "limit은 1 이상이어야 합니다") int limit // ② RequestParam
    ){
    List<BoardDTO> boards = boardService.findPostsByCategory(categoryId, limit);
        return ResponseEntity.ok(boards);
    }
    
    /** 게시글 상세 조회 → GET /boards/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoardDetail(
            @PathVariable("id") Long boardId
    ) {
        BoardDTO board = boardService.findById(boardId);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(Authentication authentication, 
                                         @Valid BoardDTO boardDto, 
                                         @RequestParam(name="file", required=false) MultipartFile file){
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        boardService.updateBoard(userId, boardDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name="id") Long boardId){
        boardService.deleteById(boardId);
        return ResponseEntity.ok().build();
    }
}
