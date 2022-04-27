package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.exception.NotAPostIdEx;
import com.stiches.fashionblog.exception.NotLogedInException;
import com.stiches.fashionblog.models.Like;
import com.stiches.fashionblog.models.Post;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.LikeRepo;
import com.stiches.fashionblog.repository.PostRepo;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final LikeRepo likeRepository;



    @Override
    public ResponseEntity<String> likePost(Integer postId, HttpSession session) {
        UserDtoTwo userDtoTwo = validateUser(session);
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotAPostIdEx("Post with ID "+postId+" does not Exist"));

        if(checkLike(postId, userDtoTwo.getId())) return ResponseEntity.ok("Already Liked");
        User user = userRepo.findById(userDtoTwo.getId()).get();
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        like = likeRepository.save(like);
        post.getLikeList().add(like);
        post.setLikeCount(post.getLikeCount() + 1);
        postRepo.save(post);

        return ResponseEntity.ok("Like successful");
    }

    @Override
    public ResponseEntity<String> unLikePost(Integer postId, HttpSession session) {
        UserDtoTwo userDtoTwo = validateUser(session);
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotAPostIdEx("Post with ID "+postId+" does not Exist"));
        if(!checkLike(postId, userDtoTwo.getId())) return ResponseEntity.ok("Post was never liked");

        User user = userRepo.findById(userDtoTwo.getId()).get();
        Like like = likeRepository.findLikeByPostAndUser(post,user);
        likeRepository.deleteById(like.getId());
        post.setLikeCount(post.getLikeCount() - 1);
        postRepo.save(post);
        return ResponseEntity.ok("Post unliked");
    }


    @Override
    public Boolean checkLike(Integer postId, Integer userId) {
        Post post = postRepo.findById(postId).get();
        User user = userRepo.findById(userId).get();

        Like like = likeRepository.findLikeByPostAndUser(post,user);
        if(like == null)return false;
        return true;
    }

    @Override
    public UserDtoTwo validateUser(HttpSession session) {
        UserDtoTwo user = (UserDtoTwo) session.getAttribute("user");
        if(user == null) throw new NotLogedInException("You have to be logged In");

        return user;
    }


}
