package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.PostDto;
import com.stiches.fashionblog.dto.PostDtoTwo;
import com.stiches.fashionblog.dto.Search;
import com.stiches.fashionblog.dto.UserDtoTwo;
import com.stiches.fashionblog.exception.NoAccess;
import com.stiches.fashionblog.exception.NotAPostIdEx;
import com.stiches.fashionblog.exception.NotAuthorized;
import com.stiches.fashionblog.exception.NotLogedInException;
import com.stiches.fashionblog.models.*;
import com.stiches.fashionblog.repository.*;
import com.stiches.fashionblog.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final LikeRepo likeRepo;
    private final CommentRepo commentRepo;



    @Override
    public ResponseEntity<PostDtoTwo> createPost(PostDto postDto, HttpSession session) {
        UserDtoTwo userdto = (UserDtoTwo) session.getAttribute("user");
        User user = null;
        try {
            user = userRepo.findById(userdto.getId()).get();
        }catch (Exception e){
            throw new NotLogedInException("You have to be LoggedIn");
        }

        if(user.getRole().equals(Role.CUSTOMER)) throw new NotAuthorized("ACCESS DENIED");

        Post post = new Post();
        post.setUser(user);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Category cat = null;

        try{
            cat = categoryRepo.findByCategoryName(postDto.getCategory()).get();
        }catch (Exception ex){
        }

        post.setCategory(cat);
        if(cat == null){
            Category cate = new Category();
            cate.setCategoryName(postDto.getCategory());

            Category cat1 = categoryRepo.save(cate);
            post.setCategory(cat1);
        }
        post = postRepo.save(post);

        user.getPosts().add(post);
        userRepo.save(user);

        return new ResponseEntity<>(mapper.map(post,PostDtoTwo.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<PostDtoTwo>> getAllPosts() {

        List<PostDtoTwo> postDtoTwo = postRepo.findAll()
                .stream()
                .map(post -> mapper.map(post,PostDtoTwo.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(postDtoTwo);
    }

    @Override
    public ResponseEntity<PostDtoTwo> findById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new NotAPostIdEx("No post with ID of "+id+" Exists"));
        return ResponseEntity.ok(mapper.map(post,PostDtoTwo.class));
    }

    @Override
    public ResponseEntity<String> deletePost(Integer postId, HttpSession session) {
        check(postId,session);
        postRepo.deleteById(postId);
        return ResponseEntity.ok("Post Deleted successfully");
    }

    @Override
    public void check(Integer postId, HttpSession session){
        UserDtoTwo user = (UserDtoTwo) session.getAttribute("user");
        if(user == null) throw new NotLogedInException("You have to be logged in");
        Optional<Post> optionalPost = postRepo.findById(postId);
        if(optionalPost.isEmpty()) throw new NotAPostIdEx("No post with ID of "+postId+" Exists");
        if(user.getId() != optionalPost.get().getUser().getId()) throw new NoAccess("You Cannot Alter this post");
    }

    @Override
    public void save(Post post) {
        postRepo.save(post);
    }



    @Override
    public ResponseEntity<PostDtoTwo> patchAndPut(HttpSession session, Integer postId, PostDto postDto) {
        check(postId,session);

        Post post = postRepo.findById(postId).get();
        if(postDto.getTitle() != null){
            post.setTitle(postDto.getTitle());
        }

        if(postDto.getContent() != null){
            post.setContent(postDto.getContent());
        }
        if(postDto.getCategory() != null){
            Category category = null;

            try {
                category = categoryRepo.findByCategoryName(postDto.getCategory()).get();
            }catch (Exception e){

            }
            if(category == null){
                Category cate = new Category();
                cate.setCategoryName(postDto.getCategory());
                category = categoryRepo.save(cate);
            }

            post.setCategory(category);
        }
        post = postRepo.save(post);

        return ResponseEntity.ok(mapper.map(post,PostDtoTwo.class));
    }

    @Override
    public ResponseEntity<List<PostDtoTwo>> search(Search search) {
        String out = search.getTitle().toLowerCase();

        List<PostDtoTwo> postDtoTwos = postRepo.findAll().stream()
                .map(post -> mapper.map(post,PostDtoTwo.class))
                .filter(p -> p.getTitle().toLowerCase().contains(out))
                .collect(Collectors.toList());

        return ResponseEntity.ok(postDtoTwos);
    }
}
