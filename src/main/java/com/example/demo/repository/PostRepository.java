package com.example.demo.repository;

import com.example.demo.domain.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository{

    GardeningPost create(Long userId, GardeningPost data, MultipartFile image);

    RecipePost create (Long userId, RecipePost data, MultipartFile image);
    IMadePost create (Long userId, IMadePost data, MultipartFile image);
    OtherPost create (Long userId, OtherPost data, MultipartFile image);

    List<GardeningPost> getAllGardeningPosts();

    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);

    List<GardeningPost> getAllGardeningPost(Pageable pageable);
    List<RecipePost> getAllRecipePost(Pageable pageable);
    List<IMadePost> getAllIMadePost(Pageable pageable);
    List<OtherPost> getAllOtherPost(Pageable pageable);

    GardeningPost getGardeningPostById(long id);

   RecipePost getRecipePostById(long id);
    IMadePost getIMadePostById(long id);
    OtherPost getOtherPostById(long id);

    int getAllRecipePostCount();
    GardeningComment addGardeningComment(Long userId, Long postId, GardeningComment gardeningComment);
    List<GardeningComment> getAllGardeningCommentsByPostId(Long postId);

    RecipeComment addRecipeComment(Long userId, Long postId, RecipeComment recipeComment);
    List<RecipeComment> getAllRecipeCommentsByPostId(Long postId);

    IMadeComment addIMadeComment(Long userId, Long postId, IMadeComment iMadeComment);
    List<IMadeComment> getAllIMadeCommentsByPostId(Long postId);

    OtherComment addOtherComment(Long userId, Long postId, OtherComment otherComment);
    List<OtherComment> getAllOtherCommentsByPostId(Long postId);
}
