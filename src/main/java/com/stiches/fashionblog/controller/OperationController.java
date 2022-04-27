package com.stiches.fashionblog.controller;

import com.stiches.fashionblog.dto.*;
import com.stiches.fashionblog.exception.NotAuthorized;
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
    public ResponseEntity<List<PostDtoTwo>> viewAllPost(){
        return postService.getAllPosts();
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDtoTwo> createPost(@RequestBody PostDto postDto, HttpSession session){
        return postService.createPost(postDto,session);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDtoTwo> viewPost(@PathVariable(name = "postId") Integer postId){
        return postService.findById(postId);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDtoTwo> viewPost(@PathVariable(name = "postId") Integer postId, @RequestBody PostDto postDto, HttpSession session){
        return postService.patchAndPut(session, postId, postDto);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto, HttpSession session){
        UserDtoTwo user = (UserDtoTwo) session.getAttribute("user");
        if(user == null) throw new NotLogedInException("You have to be logged in");
        if(user.getRole() != Role.ADMIN) throw new NotAuthorized("Reserved for Admin");
        return categoryService.createCategory(categoryDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategoryList(){
        return ResponseEntity.ok(categoryService.allCategories());
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Integer postId,HttpSession session){
        return postService.deletePost(postId,session);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable Integer postId){
        return commentService.allComentByPost(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> postAComment(@PathVariable Integer postId, @RequestBody CommentDto commentDto, HttpSession session){
        return commentService.commentOnPost(postId,session,commentDto);
    }

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<String> likePost(@PathVariable Integer postId, HttpSession session){
        return likeService.likePost(postId,session);
    }
    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<String> unlikePost(@PathVariable Integer postId, HttpSession session){
    return likeService.unLikePost(postId,session);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDtoTwo>> searchPost(@RequestBody Search search){
        return postService.search(search);
    }

}
