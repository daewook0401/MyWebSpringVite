package com.myspring.myproject.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.myspring.myproject.board.model.dto.BoardDTO;

public interface BoardService {
    void save(BoardDTO board, MultipartFile file);
    // pageNo, categoryId 를 받아서 카테고리별 페이징 조회
    List<BoardDTO> findPostsByCategory(Long categoryId, int limit);
    BoardDTO findById(Long boardId);
    void updateBoard(Long userId,BoardDTO boardDto, MultipartFile file);
    void deleteById(Long boardId);
}
