package com.stiches.fashionblog.repository;

import com.stiches.fashionblog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
    Optional<Category> findByCategoryName(String categoryName);
}
