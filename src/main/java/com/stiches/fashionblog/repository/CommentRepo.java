package com.stiches.fashionblog.repository;

import com.stiches.fashionblog.models.Comment;
import com.stiches.fashionblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findCommentsByPostId(Integer postId);
    void deleteCommentsByPost(Post post);
    void deleteCommentsByPostId(Integer postId);
    List<Comment> findCommentsByPost(Post post);
}
