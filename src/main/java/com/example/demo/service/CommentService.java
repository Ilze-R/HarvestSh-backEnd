package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;

import java.util.List;

public interface CommentService {

    List<GardeningComment> getAllGardeningCommentsByPostId(long id);
    List<RecipeComment> getAllRecipeCommentsByPostId(long id);
    List<IMadeComment> getAllIMadeCommentsByPostId(long id);
    List<OtherComment> getAllOtherCommentsByPostId(long id);
    GardeningComment addGardeningComment(long userId, long postId, GardeningComment gardeningComment);
    RecipeComment addRecipeComment(long userId, long postId, RecipeComment recipeComment);
    IMadeComment addIMadeComment(long userId, long postId, IMadeComment iMadeComment);
    OtherComment addOtherComment(long userId, long postId, OtherComment otherComment);
    void updateComment(Long commentId, String comment_text, PostType postType);
    void deleteComment(Long commentId, PostType postType);
    GardeningComment getLatestGardeningComment(long postId);
    void addCommentLike(Long id, PostType postType);
    void removeCommentLike(Long id, PostType postType);
    boolean userHasLikedComment(Long userId, Long commentId, String tableName);
    List<LikedGardeningComment> getUserLikedGardeningComments(Long userId);
    List<LikedRecipeComment> getUserLikedRecipeComments(Long userId);
    List<LikedIMadeComment> getUserLikedIMadeComments(Long userId);
    List<LikedOtherComment> getUserLikedOtherComments(Long userId);
    void addCommentLikeKeyTable(Long userId, Long commentId, PostType postType);
    void deleteCommentLikeKeyTable(Long userId, Long commentId, PostType postType);
}
