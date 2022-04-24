package com.stiches.fashionblog.controller;

import com.stiches.fashionblog.dto.CategoryDto;
import com.stiches.fashionblog.dto.CommentDto;
import com.stiches.fashionblog.dto.PostDto;
import com.stiches.fashionblog.dto.Search;
import com.stiches.fashionblog.exception.NotLogedInException;
import com.stiches.fashionblog.models.*;
import com.stiches.fashionblog.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
public class OperationController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final LikeService likeService;


    @GetMapping("/posts")
    public ResponseEntity<List<Post>> viewAllPost(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto, HttpSession session){
        User user = (User) session.getAttribute("user");
        Post post = postService.createPost(user.getId(),postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> viewPost(@PathVariable(name = "postId") Integer postId){
        return ResponseEntity.ok(postService.findById(postId));
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> viewPost(@PathVariable(name = "postId") Integer postId, @RequestBody PostDto postDto, HttpSession session){
        User user = (User) session.getAttribute("user");
        return ResponseEntity.ok(postService.patchAndPut(user.getId(), postId, postDto));
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user.getRole() != Role.ADMIN) throw new RuntimeException();
        Category category = categoryService.createCategory(categoryDto.getCategory());
        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoryList(){
        return ResponseEntity.ok(categoryService.allCategories());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Integer postId){
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getAllComment(@PathVariable Integer postId){
        return ResponseEntity.ok(commentService.allComentByPost(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> postAComment(@PathVariable Integer postId, @RequestBody CommentDto commentDto, HttpSession session){
        User user = (User) session.getAttribute("user");
        Comment comment = commentService.commentOnPost(postId,user.getId(),commentDto);
        return new ResponseEntity<>(comment,HttpStatus.CREATED);
    }

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<String> likePost(@PathVariable Integer postId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) throw new NotLogedInException("You have to be logged in");
        return new ResponseEntity<>(likeService.likePost(postId,user.getId()), HttpStatus.CREATED);
    }
    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<String> unlikePost(@PathVariable Integer postId, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) throw new NotLogedInException("You have to be Logged in");

        return new ResponseEntity<>(likeService.unLikePost(postId,user.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<Post>> searchPost(@RequestBody Search search){
        return ResponseEntity.ok(postService.search(search));
    }

}
