package com.stiches.fashionblog.service.Impl;

import com.stiches.fashionblog.dto.PostDto;
import com.stiches.fashionblog.dto.Search;
import com.stiches.fashionblog.models.*;
import com.stiches.fashionblog.repository.*;
import com.stiches.fashionblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final LikeRepo likeRepo;
    private final CommentRepo commentRepo;



    @Override
    public Post createPost(Integer adminId, PostDto postDto) {
        User user = userRepo.findById(adminId).get();
        if(user.getRole().equals(Role.CUSTOMER)) throw new RuntimeException();

        Post post = new Post();
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

        return postRepo.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post findById(Integer id) {
        Optional<Post> optionalPost = postRepo.findById(id);
        if(optionalPost.isEmpty()) throw new RuntimeException();
        return optionalPost.get();
    }

    @Override
    public String deletePost(Integer id) {
        Post post = postRepo.findById(id).get();

        List<Comment> comments = commentRepo.findCommentsByPost(post);

        for(int i = 0; i< comments.size(); i++){
            int pid = comments.get(i).getId();
            commentRepo.deleteById(pid);
        }

        List<Like> likes = likeRepo.findLikesByPost(post);

        for(int i = 0; i< likes.size(); i++){
            int lId = likes.get(i).getId();
            likeRepo.deleteById(lId);
        }
        postRepo.deleteById(id);
        return "Post deleted";
    }

    @Override
    public void save(Post post) {
        postRepo.save(post);
    }



    @Override
    public Post patchAndPut(Integer adminId,Integer postId, PostDto postDto) {
        User user = userRepo.findById(adminId).get();
        if(user.getRole().equals(Role.CUSTOMER)) throw new RuntimeException();

        Post post = findById(postId);
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
        postRepo.save(post);

        return findById(postId);
    }

    @Override
    public List<Post> search(Search search) {
        String out = search.getTitle().toLowerCase();
        List<Post> all = getAllPosts();
        List<Post> searched = new ArrayList<>();
        for (var a: all){
            String in = a.getTitle().toLowerCase();
            if(in.contains(out)){
                searched.add(a);
            }
        }
        return searched;
    }
}
