package com.stiches.fashionblog.service;

public interface LikeService {
    String likePost(Integer postId, Integer userId);
    String unLikePost(Integer postId, Integer userId);
    Boolean checkLike(Integer postId, Integer userId);

}
