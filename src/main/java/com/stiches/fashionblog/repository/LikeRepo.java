package com.stiches.fashionblog.repository;

import com.stiches.fashionblog.models.Like;
import com.stiches.fashionblog.models.Post;
import com.stiches.fashionblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Like,Integer> {
    Like findLikeByPostAndUser(Post post, User user);
    void deleteLikesByPostId(Long postId);
    List<Like> findLikesByPost(Post post);
}
