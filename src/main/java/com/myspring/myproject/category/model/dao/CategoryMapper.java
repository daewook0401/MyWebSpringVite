package com.myspring.myproject.category.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.myspring.myproject.category.model.dto.CategoryDTO;
import com.myspring.myproject.category.model.dto.FavoriteCategoryDTO;
import com.myspring.myproject.category.model.vo.Category;

@Mapper
public interface CategoryMapper {
    List<CategoryDTO> getCategoriesWithPosts(int limit);

    List<CategoryDTO> findAll();

    void insertCategory(Category category);

    void deleteById(Long categoryId);

    List<FavoriteCategoryDTO> selectFavoritesByUserId(Long userId);

    int deleteFavoriteByUserAndCategory(Map<String, Object> params);

    void setFavoriteCategories(FavoriteCategoryDTO favoriteCategoryDTO);
}
