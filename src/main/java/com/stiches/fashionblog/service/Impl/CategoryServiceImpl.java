package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.CategoryDto;
import com.stiches.fashionblog.models.Category;
import com.stiches.fashionblog.repository.CategoryRepo;
import com.stiches.fashionblog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;


    @Override
    public Category createCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category = categoryRepo.save(category);
        return category;
    }

    @Override
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto) {
        CategoryDto catDto = mapper.map(createCategory(categoryDto.getCategory()),CategoryDto.class);
        return new ResponseEntity<>(catDto, HttpStatus.CREATED);
    }

    @Override
    public Category findByCategory(String categoryName){
        Optional<Category> optionalCategory = categoryRepo.findByCategoryName(categoryName);

        return optionalCategory.orElse(null);
    }

    @Override
    public List<Category> allCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public ResponseEntity<String> deleteCategory(Integer categoryId) {
       categoryRepo.deleteById(categoryId);
        return ResponseEntity.ok("category deleted");
    }
}
