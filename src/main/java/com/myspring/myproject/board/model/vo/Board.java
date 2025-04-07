package com.myspring.myproject.board.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Board {

    private Long boardId;
    private Long boardWriter;
    private String boardTitle;
    private String boardContent;
    private Long boardCategory;
    private String boardFileUrl;
    private Date createdAt;
    private Date updatedAt;
}
