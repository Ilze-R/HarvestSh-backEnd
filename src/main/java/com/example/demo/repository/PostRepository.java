package com.example.demo.repository;

import com.example.demo.domain.*;

import com.example.demo.enumeration.PostType;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostRepository {

    <T extends Post> T create(Long userId, T post, MultipartFile image);

    GardeningPost createGardeningPostNoPhoto(long userId, GardeningPost gardeningPost);

    void deletePost(Long postId, PostType postType);

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

    RecipeComment addRecipeComment(Long userId, Long postId, RecipeComment recipeComment);

    IMadeComment addIMadeComment(Long userId, Long postId, IMadeComment iMadeComment);

    OtherComment addOtherComment(Long userId, Long postId, OtherComment otherComment);

    List<GardeningComment> getAllGardeningCommentsByPostId(Long postId);

    List<RecipeComment> getAllRecipeCommentsByPostId(Long postId);

    List<IMadeComment> getAllIMadeCommentsByPostId(Long postId);

    List<OtherComment> getAllOtherCommentsByPostId(Long postId);

    void updateComment(Long commentId, String comment_text, PostType postType);

    void deleteComment(Long commentId, PostType postType);

    GardeningComment getLatestGardeningComment(Long postId);

    // GARDENING LIKES

    void addGardeningPostLikeKeyTable(Long userId, Long postId);

    void deleteGardeningPostLikeKeyTable(Long userId, Long postId);

    boolean userHasLikedGardeningPost(Long userId, Long postId);

    List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId);
//    GardeningComment getGardeningCommentById(long id);

    ////////////////

    void addCommentLike(Long id, PostType postType);
    void removeCommentLike(Long id, PostType postType);

    void addGardeningCommentLikeKeyTable(Long userId, Long commentId);

    void deleteGardeningCommentLikeKeyTable(Long userId, Long commentId);

    boolean userHasLikedGardeningComment(Long userId, Long commentId);

    List<LikedGardeningComment> getUserLikedGardeningComments(Long userId);

    void addPostLike(Long id, PostType postType);

    void removePostLike(Long id, PostType postType);

    // RECIPE LIKES
    void addRecipePostLikeKeyTable(Long userId, Long postId);

    void deleteRecipePostLikeKeyTable(Long userId, Long postId);

    boolean userHasLikedRecipePost(Long userId, Long postId);

    List<LikedRecipePost> getUserLikedRecipePosts(Long userId);

    ///////////////

    void addRecipeCommentLikeKeyTable(Long userId, Long commentId);

    void deleteRecipeCommentLikeKeyTable(Long userId, Long commentId);

    boolean userHasLikedRecipeComment(Long userId, Long commentId);

    List<LikedRecipeComment> getUserLikedRecipeComments(Long userId);

    // I MADE LIKES

    void addIMadePostLikeKeyTable(Long userId, Long postId);

    void deleteIMadePostLikeKeyTable(Long userId, Long postId);

    boolean userHasLikedIMadePost(Long userId, Long postId);

    List<LikedIMadePost> getUserLikedIMadePosts(Long userId);

    //////////////

    void addIMadeCommentLikeKeyTable(Long userId, Long commentId);

    void deleteIMadeCommentLikeKeyTable(Long userId, Long commentId);

    boolean userHasLikedIMadeComment(Long userId, Long commentId);

    List<LikedIMadeComment> getUserLikedIMadeComments(Long userId);
    // OTHER LIKES


    void addOtherPostLikeKeyTable(Long userId, Long postId);

    void deleteOtherPostLikeKeyTable(Long userId, Long postId);

    boolean userHasLikedOtherPost(Long userId, Long postId);

    List<LikedOtherPost> getUserLikedOtherPosts(Long userId);

    ///////////

    void addOtherCommentLikeKeyTable(Long userId, Long commentId);

    void deleteOtherCommentLikeKeyTable(Long userId, Long commentId);

    boolean userHasLikedOtherComment(Long userId, Long commentId);

    List<LikedOtherComment> getUserLikedOtherComments(Long userId);
}
