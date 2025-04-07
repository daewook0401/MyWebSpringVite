package com.myspring.myproject.comments.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;


@Getter
@Value
@Builder
public class Comment {
    private Long commentId;
    private Long boardId;
    private Long commentWriter;
    private String commentContent;
    private Date createAt;
    private Date updateAt;
}
