package com.myspring.myproject.comments.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
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
public class CommentDTO {
    private Long commentId;
    private Long boardId;
    private Long commentWriter;

    @NotBlank(message = "댓글을 작성해 주십시오.")
    private String commentContent;
    private Date createdAt;
    private Date updatedAt;
}
