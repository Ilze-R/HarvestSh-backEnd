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

    void deleteGardeningPost(Long postId);
    void deleteRecipePost(Long postId);
    void deleteIMadePost(Long postId);
    void deleteOtherPost(Long postId);

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

    GardeningComment getLatestGardeningComment(long postId);

    //   GARDENING POST
    void updatePlusGardeningLike(Long id);
    void updateMinusGardeningLike(Long id);
    void addGardeningPostLikeKeyTable(Long userId, Long postId);
    void deleteGardeningPostLikeKeyTable(Long userId, Long postId);

    int getAllGardeningPostLikes(Long postId);

    boolean userHasLikedGardeningPost(Long userId, Long postId);

    List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId);

    GardeningComment getGardeningCommentById(long id);

    ///////////////////////////////////////////////

    void updatePlusGardeningCommentLike(Long id);
    void updateMinusGardeningCommentLike(Long id);
    void addGardeningCommentLikeKeyTable(Long userId, Long commentId);
    void deleteGardeningCommentLikeKeyTable(Long userId, Long commentId);

    int getAllGardeningCommentLikes(Long postId);

    boolean userHasLikedGardeningComment(Long userId, Long commentId);

    List<LikedGardeningComment> getUserLikedGardeningComments(Long commentId);

    //   RECIPE POST

    void updatePlusRecipeLike(Long id);
    void updateMinusRecipeLike(Long id);
    void addRecipePostLikeKeyTable(Long userId, Long postId);
    void deleteRecipePostLikeKeyTable(Long userId, Long postId);

    int getAllRecipePostLikes(Long postId);

    boolean userHasLikedRecipePost(Long userId, Long postId);

    List<LikedRecipePost> getUserLikedRecipePosts(Long userId);

    //   IMADE POST
    void updatePlusIMadeLike(Long id);
    void updateMinusIMadeLike(Long id);
    void addIMadePostLikeKeyTable(Long userId, Long postId);
    void deleteIMadePostLikeKeyTable(Long userId, Long postId);

    int getAllIMadePostLikes(Long postId);

    boolean userHasLikedIMadePost(Long userId, Long postId);

    List<LikedIMadePost> getUserLikedIMadePosts(Long userId);

    //   OTHER POST

    void updatePlusOtherLike(Long id);
    void updateMinusOtherLike(Long id);
    void addOtherPostLikeKeyTable(Long userId, Long postId);
    void deleteOtherPostLikeKeyTable(Long userId, Long postId);

    int getAllOtherPostLikes(Long postId);

    boolean userHasLikedOtherPost(Long userId, Long postId);

    List<LikedOtherPost> getUserLikedOtherPosts(Long userId);

}
