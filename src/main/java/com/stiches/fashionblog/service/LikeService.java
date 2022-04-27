package com.stiches.fashionblog.service;

import com.stiches.fashionblog.dto.UserDtoTwo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface LikeService {
    ResponseEntity<String> likePost(Integer postId,HttpSession session);
    ResponseEntity<String> unLikePost(Integer postId, HttpSession session);
    Boolean checkLike(Integer postId, Integer userId);
    UserDtoTwo validateUser(HttpSession session);

}
