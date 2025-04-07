package com.myspring.myproject.category.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteCategoryDTO {
    private Long favoriteId;
    private Long userId;
    private Long categoryId;
    private Date createdAt;
}
