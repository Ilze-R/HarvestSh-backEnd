package com.example.demo.repository;

import com.example.demo.domain.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository{

    GardeningPost create(Long userId, GardeningPost data, MultipartFile image);

    RecipePost create (Long userId, RecipePost data, MultipartFile image);

 IMadePost create(Long userId, IMadePost data, MultipartFile image);

 OtherPost create(Long userId, OtherPost data, MultipartFile image);

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

    GardeningComment getLatestGardeningComment(Long postId);

    // GARDENING LIKES

    void addGardeningLike(Long id);

    void deleteGardeningLike(Long id);

    void addGardeningPostLikeKeyTable(Long userId, Long postId);

    void deleteGardeningPostLikeKeyTable(Long userId, Long postId);

    int getAllGardeningPostLikes(Long postId);

    boolean userHasLikedGardeningPost(Long userId, Long postId);

    List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId);
//    GardeningComment getGardeningCommentById(long id);

    ////////////////

    void addGardeningCommentLike(Long id);

    void deleteGardeningCommentLike(Long id);

    void addGardeningCommentLikeKeyTable(Long userId, Long commentId);

    void deleteGardeningCommentLikeKeyTable(Long userId, Long commentId);

    int getAllGardeningCommentLikes(Long commentId);

    boolean userHasLikedGardeningComment(Long userId, Long commentId);

    List<LikedGardeningComment> getUserLikedGardeningComments(Long userId);


    // RECIPE LIKES

    void addRecipeLike(Long id);

    void deleteRecipeLike(Long id);

    void addRecipePostLikeKeyTable(Long userId, Long postId);

    void deleteRecipePostLikeKeyTable(Long userId, Long postId);

    int getAllRecipePostLikes(Long postId);

    boolean userHasLikedRecipePost(Long userId, Long postId);

    List<LikedRecipePost> getUserLikedRecipePosts(Long userId);

    ///////////////

 void addRecipeCommentLike(Long id);

 void deleteRecipeCommentLike(Long id);

 void addRecipeCommentLikeKeyTable(Long userId, Long commentId);

 void deleteRecipeCommentLikeKeyTable(Long userId, Long commentId);

 int getAllRecipeCommentLikes(Long commentId);

 boolean userHasLikedRecipeComment(Long userId, Long commentId);

 List<LikedRecipeComment> getUserLikedRecipeComments(Long userId);

    // I MADE LIKES

    void addIMadeLike(Long id);

    void deleteIMadeLike(Long id);

    void addIMadePostLikeKeyTable(Long userId, Long postId);

    void deleteIMadePostLikeKeyTable(Long userId, Long postId);

    int getAllIMadePostLikes(Long postId);

    boolean userHasLikedIMadePost(Long userId, Long postId);

    List<LikedIMadePost> getUserLikedIMadePosts(Long userId);

    //////////////

 void addIMadeCommentLike(Long id);

 void deleteIMadeCommentLike(Long id);

 void addIMadeCommentLikeKeyTable(Long userId, Long commentId);

 void deleteIMadeCommentLikeKeyTable(Long userId, Long commentId);

 int getAllIMadeCommentLikes(Long commentId);

 boolean userHasLikedIMadeComment(Long userId, Long commentId);

 List<LikedIMadeComment> getUserLikedIMadeComments(Long userId);
    // OTHER LIKES

    void addOtherLike(Long id);

    void deleteOtherLike(Long id);

    void addOtherPostLikeKeyTable(Long userId, Long postId);

    void deleteOtherPostLikeKeyTable(Long userId, Long postId);

    int getAllOtherPostLikes(Long postId);

    boolean userHasLikedOtherPost(Long userId, Long postId);

    List<LikedOtherPost> getUserLikedOtherPosts(Long userId);

    ///////////

 void addOtherCommentLike(Long id);

 void deleteOtherCommentLike(Long id);

 void addOtherCommentLikeKeyTable(Long userId, Long commentId);

 void deleteOtherCommentLikeKeyTable(Long userId, Long commentId);

 int getAllOtherCommentLikes(Long commentId);

 boolean userHasLikedOtherComment(Long userId, Long commentId);

 List<LikedOtherComment> getUserLikedOtherComments(Long userId);
}
