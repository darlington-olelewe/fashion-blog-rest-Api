package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.CommentDto;
import com.stiches.fashionblog.models.Comment;

import java.util.List;

public interface CommentService {
    Comment commentOnPost(Integer postId, Integer userId, CommentDto commentDto);
    Long commentSize(Integer postId);
    List<Comment> allComentByPost(Integer postId);
    String deleteComments(Integer postId);
}
