package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.models.Like;
import com.stiches.fashionblog.models.Post;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.LikeRepo;
import com.stiches.fashionblog.repository.PostRepo;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepo postService;
    private final UserRepo userService;
    private final LikeRepo likeRepository;



    @Override
    public String likePost(Integer postId, Integer userId) {
        Post post = postService.findById(postId).get();
        User user = userService.findById(userId).get();
        if(checkLike(postId,userId)) return "Already Liked";

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like = likeRepository.save(like);
        post.getLikeList().add(like);
        post.setLikeCount(post.getLikeCount() + 1);
        postService.save(post);

        return "Like successful";
    }

    @Override
    public String unLikePost(Integer postId, Integer userId) {
        Post post = postService.findById(postId).get();
        User user = userService.findById(userId).get();
        if(!checkLike(postId,userId)) return "Post was never liked";
        Like like = likeRepository.findLikeByPostAndUser(post,user);
        likeRepository.deleteById(like.getId());
        post.setLikeCount(post.getLikeCount() - 1);
        postService.save(post);
        return "Post unliked";
    }

    @Override
    public Boolean checkLike(Integer postId, Integer userId) {
        Post post = postService.findById(postId).get();
        User user = userService.findById(userId).get();

        Like like = likeRepository.findLikeByPostAndUser(post,user);
        if(like == null)return false;
        return true;
    }


}
