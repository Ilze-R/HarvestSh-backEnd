package com.example.demo.repository;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;

import java.util.List;

public interface CommentRepository {

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
    void addCommentLikeKeyTable(Long userId, Long commentId, PostType postType);
    void deleteCommentLikeKeyTable(Long userId, Long commentId, PostType postType);
    void addCommentLike(Long id, PostType postType);
    void removeCommentLike(Long id, PostType postType);
    boolean userHasLikedComment(Long userId, Long commentId, String tableName);
    List<LikedGardeningComment> getUserLikedGardeningComments(Long userId);
    List<LikedRecipeComment> getUserLikedRecipeComments(Long userId);
    List<LikedIMadeComment> getUserLikedIMadeComments(Long userId);
    List<LikedOtherComment> getUserLikedOtherComments(Long userId);
}
