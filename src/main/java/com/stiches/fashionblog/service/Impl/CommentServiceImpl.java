package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.CommentDto;
import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.exception.NotAPostIdEx;
import com.stiches.fashionblog.exception.NotLogedInException;
import com.stiches.fashionblog.models.Comment;
import com.stiches.fashionblog.models.Post;
import com.stiches.fashionblog.models.User;
import com.stiches.fashionblog.repository.CommentRepo;
import com.stiches.fashionblog.repository.PostRepo;
import com.stiches.fashionblog.repository.UserRepo;
import com.stiches.fashionblog.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final ModelMapper mapper;



    @Override
    public ResponseEntity<CommentDto> commentOnPost(Integer postId, HttpSession session, CommentDto commentDto) {

        UserDtoTwo userD = (UserDtoTwo) session.getAttribute("user");
        if(userD == null) throw new NotLogedInException("You have to be logged in");

        User user = userRepo.findById(userD.getId()).get();
        Post post  = postRepo.findById(postId).orElseThrow(() -> new NotAPostIdEx("This Id does not belong to a product"));

        Comment comment = new Comment();
        comment.setInfo(commentDto.getInfo());
        comment.setUser(user);
        comment.setPost(post);
        comment = commentRepo.save(comment);

        post.getCommentList().add(comment);
        post.setCommentCount(post.getCommentCount() + 1);

        postRepo.save(post);

        return new ResponseEntity<>(mapper.map(comment,CommentDto.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CommentDto>> allComentByPost(Integer postId) {
        List<CommentDto> all = commentRepo.findCommentsByPostId(postId).stream().map(comment -> mapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<String> deleteComments(Integer postId) {
        commentRepo.deleteCommentsByPostId(postId);
        return ResponseEntity.ok("deleted");
    }
}
