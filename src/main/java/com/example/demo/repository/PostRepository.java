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

    void updateGardeningComment(Long commentId, String comment_text);

    void updateRecipeComment(Long commentId, String comment_text);
    void updateIMadeComment(Long commentId, String comment_text);
    void updateOtherComment(Long commentId, String comment_text);

    void deleteGardeningComment(Long commentId);
    void deleteRecipeComment(Long commentId);
    void deleteIMadeComment(Long commentId);
    void deleteOtherComment(Long commentId);

    void addGardeningLike(Long id);

    void deleteGardeningLike(Long id);

    void addPostLikeKeyTable(Long userId, Long postId);

    void deletePostLikeKeyTable(Long userId, Long postId);

    int getAllGardeningPostLikes(Long postId);

    boolean userHasLikedPost(Long userId, Long postId);

    List<LikedGardeningPost> getUserLikedPosts(Long userId);
    GardeningComment getGardeningCommentById(long id);
}
