package com.myspring.myproject.board.model.dto;
import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {

    /** 게시글 고유 ID (IDENTITY) */
    private Long boardId;

    /** 작성자 회원 ID (NOT NULL) */
    @NotNull(message = "작성자 ID는 필수입니다.")
    @Min(value = 1, message = "작성자 ID는 1 이상의 값이어야 합니다.")
    private Long boardWriter;

    /** 게시글 제목 (255자 이하, NOT NULL) */
    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Size(max = 255, message = "제목은 최대 255자까지 입력 가능합니다.")
    private String boardTitle;

    /** 게시글 내용 (NOT NULL) */
    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    private String boardContent;

    /** 카테고리 ID (NOT NULL) */
    @NotNull(message = "카테고리 ID는 필수입니다.")
    @Min(value = 1, message = "카테고리 ID는 1 이상의 값이어야 합니다.")
    private Long boardCategory;
    private String boardFileUrl;
    /** 생성 시각 (DEFAULT CURRENT_TIMESTAMP) */
    private Date createdAt;

    /** 수정 시각 (DEFAULT CURRENT_TIMESTAMP) */
    private Date updatedAt;
    private String userName;
}