package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.CategoryDto;
import com.stiches.fashionblog.models.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService{
    Category createCategory(String categoryName);
    ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto);
    Category findByCategory(String categoryName);
    List<Category> allCategories();

    ResponseEntity<String> deleteCategory(Integer categoryId);
}
