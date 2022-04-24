package com.stiches.fashionblog.service;

import com.stiches.fashionblog.models.Category;

import java.util.List;

public interface CategoryService{
    Category createCategory(String categoryName);
    Category findByCategory(String categoryName);
    List<Category> allCategories();
}
