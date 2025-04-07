package com.myspring.myproject.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.myproject.auth.model.vo.CustomUserDetails;
import com.myspring.myproject.category.model.dto.CategoryDTO;
import com.myspring.myproject.category.model.dto.FavoriteCategoryDTO;
import com.myspring.myproject.category.model.service.CategoryService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategoriesWithPosts(
            @RequestParam(name = "limit", defaultValue = "5")
            @Min(value = 1, message = "limit은 1 이상이어야 합니다") int limit
    ) {
        List<CategoryDTO> list = categoryService.getCategoriesWithPosts(limit);
        return ResponseEntity.ok(list);
    }
    @PostMapping
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteCategoryDTO>> getFavoriteCategories(Authentication authentication) {
        // CustomUserDetails는 Security에서 사용하는 사용자 상세 클래스로, getUserId() 메서드를 제공한다고 가정합니다.
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        List<FavoriteCategoryDTO> favorites = categoryService.getFavoriteCategories(userId);
        return ResponseEntity.ok(favorites);
    }
    @PostMapping("/favorite/{categoryId}")
    public ResponseEntity<?> createFavoriteCategory(Authentication authentication,
                                                    @PathVariable("categoryId") Long categoryId){
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        categoryService.setFavoriteCategories(userId, categoryId);
        return null;
    }

    @DeleteMapping("/favorite/{categoryId}")
    public ResponseEntity<?> deleteFavoriteCategory(Authentication authentication,
                                                    @PathVariable("categoryId") Long categoryId) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        categoryService.deleteFavorite(userId, categoryId);
        return ResponseEntity.ok().build();
    }
}