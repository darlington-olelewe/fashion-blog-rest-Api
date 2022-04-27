package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.CommentDto;
import com.stiches.fashionblog.models.Comment;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommentService {
    ResponseEntity<CommentDto> commentOnPost(Integer postId, HttpSession session, CommentDto commentDto);
    ResponseEntity<List<CommentDto>> allComentByPost(Integer postId);
    ResponseEntity<String> deleteComments(Integer postId);
}
