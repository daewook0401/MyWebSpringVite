package com.myspring.myproject.category.model.service;

import java.util.List;

import com.myspring.myproject.category.model.dto.CategoryDTO;
import com.myspring.myproject.category.model.dto.FavoriteCategoryDTO;

public interface CategoryService {
    List<CategoryDTO> getCategoriesWithPosts(int limit);
    void createCategory(CategoryDTO categoryDTO);
    void deleteById(Long categoryId);
    List<FavoriteCategoryDTO> getFavoriteCategories(Long userId);
    void deleteFavorite(Long userId, Long categoryId);
    void setFavoriteCategories(Long userId, Long categoryId);
}
