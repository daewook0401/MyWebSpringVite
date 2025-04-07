package com.myspring.myproject.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.auth.service.AuthService;
import com.myspring.myproject.board.model.dao.BoardMapper;
import com.myspring.myproject.board.model.dto.BoardDTO;
import com.myspring.myproject.board.model.vo.Board;
import com.myspring.myproject.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final AuthService authService;
    private final FileService fileService;

    @Override
    public void save(BoardDTO boardDto, MultipartFile file) {
        // 1) 로그인한 사용자 ID 세팅
        CustomUserDetails user = authService.getUserDetails();
        boardDto.setBoardWriter(user.getUserId());

        // 2) VO 빌더 준비 (카테고리 포함)
        Board.BoardBuilder builder = Board.builder()
            .boardTitle(boardDto.getBoardTitle())
            .boardContent(boardDto.getBoardContent())
            .boardWriter(boardDto.getBoardWriter())
            .boardCategory(boardDto.getBoardCategory()); // 카테고리 ID

        // 3) 파일이 있으면 FileService 호출 → UUID로 이름 바꾸고 URL 리턴
        if (file != null && !file.isEmpty()) {
            String fileUrl = fileService.store(file);
            builder.boardFileUrl(fileUrl);
        }
        // 4) 최종 VO 생성 및 저장
        Board board = builder.build();
        boardMapper.save(board);
    }
    public List<BoardDTO> findPostsByCategory(Long categoryId, int limit) {
        RowBounds rb = new RowBounds(0, limit);
        List<BoardDTO> post = boardMapper.findByCategoryAll(categoryId, rb);
        return post;
    }
    @Override
    public BoardDTO findById(Long boardId) {
        return getBoardOrThrow(boardId);
    }
    private BoardDTO getBoardOrThrow(Long boardId){
        BoardDTO board = boardMapper.findById(boardId);
        if(board == null){
            throw new RuntimeException("존재하지 않는 게시글 요청입니다.");
        }
        return board;
    }
    @Override
    public void deleteById(Long boardId) {
        boardMapper.deleteById(boardId);
    }
    @Override
    public void updateBoard(Long userId,BoardDTO boardDto, MultipartFile file) {
        CustomUserDetails user = authService.getUserDetails();
        boardDto.setBoardWriter(user.getUserId());
        Board.BoardBuilder builder = Board.builder()
            .boardTitle(boardDto.getBoardTitle())
            .boardContent(boardDto.getBoardContent())
            .boardWriter(boardDto.getBoardWriter())
            .boardCategory(boardDto.getBoardCategory()); // 카테고리 ID
        if (file != null && !file.isEmpty()) {
            String fileUrl = fileService.store(file);
            builder.boardFileUrl(fileUrl);
        }
    }
}
