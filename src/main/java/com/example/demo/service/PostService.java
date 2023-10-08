package com.example.demo.service;

import com.example.demo.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    GardeningPost createGardeningPost(long userId, GardeningPost gardeningPost, MultipartFile image);
    RecipePost createRecipePost(long userId, RecipePost recipePost, MultipartFile image);

    IMadePost createIMadePost(long userId, IMadePost iMadePost, MultipartFile image);

    OtherPost createOtherPost(long userId, OtherPost otherPost, MultipartFile image);
    List<GardeningPost> getAllGardeningPosts();

    List<GardeningPost> getAllGardeningPosts(int pageSize, int offset);

    List<GardeningPost> getAllGardeningPost(Pageable pageable);
    List<RecipePost> getAllRecipePost(Pageable pageable);
    List<IMadePost> getAllIMadePost(Pageable pageable);
    List<OtherPost> getAllOtherPost(Pageable pageable);
    int getAllRecipePostCount();
    GardeningPost getGardeningPostById(long id);

    RecipePost getRecipePostById(long id);

    IMadePost getIMadePostById(long id);

   OtherPost getOtherPostById(long id);

    List<GardeningComment> getAllGardeningCommentsByPostId(long id);
    GardeningComment addGardeningComment (long userId, long postId, GardeningComment gardeningComment);

    List<RecipeComment> getAllRecipeCommentsByPostId(long id);
    RecipeComment addRecipeComment (long userId, long postId, RecipeComment recipeComment);

    List<IMadeComment> getAllIMadeCommentsByPostId(long id);
    IMadeComment addIMadeComment (long userId, long postId, IMadeComment iMadeComment);

    List<OtherComment> getAllOtherCommentsByPostId(long id);
    OtherComment addOtherComment (long userId, long postId, OtherComment otherComment);

    void updateGardeningComment(Long commentId, String comment_text);
    void updateRecipeComment(Long commentId, String comment_text);
    void updateIMadeComment(Long commentId, String comment_text);
    void updateOtherComment(Long commentId, String comment_text);

    void deleteGardeningComment(Long commentId);
    void deleteRecipeComment(Long commentId);
    void deleteIMadeComment(Long commentId);
    void deleteOtherComment(Long commentId);

    void updatePlusGardeningLike(Long id);
    void updateMinusGardeningLike(Long id);
    void addPostLikeKeyTable(Long userId, Long postId);
    void deletePostLikeKeyTable(Long userId, Long postId);

    int getAllGardeningPostLikes(Long postId);

    boolean userHasLikedPost(Long userId, Long postId);

    GardeningComment getGardeningCommentById(long id);
}
