package com.myspring.myproject.category.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.auth.service.AuthService;
import com.myspring.myproject.board.model.dao.BoardMapper;
import com.myspring.myproject.category.model.dao.CategoryMapper;
import com.myspring.myproject.category.model.dto.CategoryDTO;
import com.myspring.myproject.category.model.dto.FavoriteCategoryDTO;
import com.myspring.myproject.category.model.dto.PostDTO;
import com.myspring.myproject.category.model.vo.Category;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final AuthService authService;
    private final CategoryMapper categoryMapper;
    private final BoardMapper boardMapper;

    @Override
    public List<CategoryDTO> getCategoriesWithPosts(int limit) {
        // 1) 모든 카테고리 조회
        List<CategoryDTO> categories = categoryMapper.findAll();

        // 2) 각 카테고리별 limit 개 게시글 조회하여 DTO로 묶기
    return categories.stream()
            .map(cat -> {
                RowBounds rb = new RowBounds(0, limit);
                List<PostDTO> posts = boardMapper.findByCategory(cat.getCategoryId(), rb);
                return new CategoryDTO(
                    cat.getCategoryId(),
                    cat.getCategoryName(),
                    cat.getCategoryId(),
                    posts
                );
            })
            .collect(Collectors.toList());
    }
    @Override
    public void createCategory(CategoryDTO categoryDTO) {
        // 1) 현재 로그인한 사용자의 ID를 creatorId 로 설정
        CustomUserDetails user = authService.getUserDetails();
        Category newCategory = 
        Category.builder()
                .categoryId(categoryDTO.getCategoryId())
                .categoryName(categoryDTO.getCategoryName())
                .creatorId(user.getUserId())
                .build();
        // 2) 매퍼 호출
        categoryMapper.insertCategory(newCategory);
    }
    @Override
    public void deleteById(Long categoryId) {
        categoryMapper.deleteById(categoryId);
    }

    @Override
    public List<FavoriteCategoryDTO> getFavoriteCategories(Long userId) {
        return categoryMapper.selectFavoritesByUserId(userId);
    }
    @Override
    public void deleteFavorite(Long userId, Long categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("categoryId", categoryId);
        categoryMapper.deleteFavoriteByUserAndCategory(params);
    }
    @Override
    public void setFavoriteCategories(Long userId, Long categoryId) {
        FavoriteCategoryDTO favoriteCategoryDTO = new FavoriteCategoryDTO();
        favoriteCategoryDTO.setCategoryId(categoryId);
        favoriteCategoryDTO.setUserId(userId);
        categoryMapper.setFavoriteCategories(favoriteCategoryDTO);
    }
}
