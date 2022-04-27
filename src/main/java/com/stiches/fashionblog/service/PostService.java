package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.PostDto;
import com.stiches.fashionblog.dto.PostDtoTwo;
import com.stiches.fashionblog.dto.Search;
import com.stiches.fashionblog.models.Post;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {
    ResponseEntity<PostDtoTwo> createPost(PostDto postDto, HttpSession session);

    ResponseEntity<List<PostDtoTwo>> getAllPosts();
    ResponseEntity<PostDtoTwo> findById(Integer id);

    ResponseEntity<String> deletePost(Integer postId, HttpSession session);

    void save(Post post);

    ResponseEntity<PostDtoTwo> patchAndPut(HttpSession session, Integer postId, PostDto postDto);

    ResponseEntity<List<PostDtoTwo>> search(Search search);

    void check (Integer postId, HttpSession session);
}
