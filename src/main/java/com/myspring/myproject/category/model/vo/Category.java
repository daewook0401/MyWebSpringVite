package com.myspring.myproject.category.model.vo;

import java.util.List;

import com.myspring.myproject.category.model.dto.PostDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Category {
    private final Long   categoryId;
    private final String categoryName;
    private final Number creatorId;
    private final List<PostDTO> posts;
}
