package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.PostDto;
import com.stiches.fashionblog.dto.Search;
import com.stiches.fashionblog.models.Post;

import java.util.List;

public interface PostService {
    Post createPost(Integer adminId, PostDto postDto);

    List<Post> getAllPosts();
    Post findById(Integer id);
    String deletePost(Integer id);

    void save(Post post);

    Post patchAndPut(Integer adminId, Integer postId, PostDto postDto);

    List<Post> search(Search search);
}
