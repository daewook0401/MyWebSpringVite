package com.myspring.myproject.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.myspring.myproject.board.model.dto.BoardDTO;
import com.myspring.myproject.board.model.vo.Board;
import com.myspring.myproject.category.model.dto.PostDTO;

@Mapper
public interface BoardMapper {
    void save(Board board);

    List<BoardDTO> findAll(RowBounds rb);

    BoardDTO findById(Long boardNo);

    void update(BoardDTO board);

    void deleteById(Long boardNo);

    List<PostDTO> findByCategory(Long categoryId,RowBounds rowBounds);

    List<BoardDTO> findByCategoryAll(Long categoryId, RowBounds rowBounds);
    
}
