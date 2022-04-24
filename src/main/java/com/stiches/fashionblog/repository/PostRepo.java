package com.stiches.fashionblog.repository;

import com.stiches.fashionblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
}
