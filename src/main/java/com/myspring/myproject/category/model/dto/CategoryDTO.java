package com.myspring.myproject.category.model.dto;

import java.util.List;

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
public class CategoryDTO {

    private Long   categoryId;
    private String categoryName;
    private Long creatorId;
    private List<PostDTO> posts;
}
