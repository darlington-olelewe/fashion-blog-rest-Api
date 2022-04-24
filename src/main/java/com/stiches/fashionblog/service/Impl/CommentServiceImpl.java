package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.CommentDto;
import com.stiches.fashionblog.models.Comment;
import com.stiches.fashionblog.models.Post;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.CommentRepo;
import com.stiches.fashionblog.repository.PostRepo;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;



    @Override
    public Comment commentOnPost(Integer postId, Integer userId, CommentDto commentDto) {

        User user = userRepo.findById(userId).get();
        Post post  = postRepo.findById(postId).get();

        Comment comment = new Comment();
        comment.setInfo(commentDto.getComment());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepo.save(comment);

        post.getCommentList().add(comment);
        post.setCommentCount(post.getCommentCount() + 1);

        postRepo.save(post);

        return comment;
    }


    @Override
    public Long commentSize(Integer postId) {
        return (long) allComentByPost(postId).size();
    }

    @Override
    public List<Comment> allComentByPost(Integer postId) {
        return commentRepo.findCommentsByPostId(postId);
    }

    @Override
    public String deleteComments(Integer postId) {
        commentRepo.deleteCommentsByPostId(postId);
        return "deleted";
    }
}
