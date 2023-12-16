package com.example.demo.service.implementation;

import com.example.demo.domain.*;
import com.example.demo.enumeration.CommentAction;
import com.example.demo.enumeration.PostType;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public void addCommentLikeKeyTable(Long userId, Long commentId, PostType postType) {
        commentRepository.addCommentLikeKeyTable(userId, commentId, postType);
    }

    @Override
    public void deleteCommentLikeKeyTable(Long userId, Long commentId, PostType postType) {
        commentRepository.deleteCommentLikeKeyTable(userId, commentId, postType);
    }

    @Override
    public GardeningComment addGardeningComment(long userId, long postId, GardeningComment gardeningComment) {
        return commentRepository.addGardeningComment(userId, postId, gardeningComment);

    }
    @Override
    public IMadeComment addIMadeComment(long userId, long postId, IMadeComment iMadeComment) {
        return commentRepository.addIMadeComment(userId, postId, iMadeComment);
    }
    @Override
    public RecipeComment addRecipeComment(long userId, long postId, RecipeComment recipeComment) {
        return commentRepository.addRecipeComment(userId, postId, recipeComment);
    }

    @Override
    public OtherComment addOtherComment(long userId, long postId, OtherComment otherComment) {
        return commentRepository.addOtherComment(userId, postId, otherComment);
    }

    @Override
    public List<GardeningComment> getAllGardeningCommentsByPostId(long id) {
        return commentRepository.getAllGardeningCommentsByPostId(id);
    }

    @Override
    public List<RecipeComment> getAllRecipeCommentsByPostId(long id) {
        return commentRepository.getAllRecipeCommentsByPostId(id);
    }

    @Override
    public List<IMadeComment> getAllIMadeCommentsByPostId(long id) {
        return commentRepository.getAllIMadeCommentsByPostId(id);
    }

    @Override
    public List<OtherComment> getAllOtherCommentsByPostId(long id) {
        return commentRepository.getAllOtherCommentsByPostId(id);
    }

    @Override
    public Long getGardeningCommentPostId(Long id) {
        return commentRepository.getGardeningCommentPostId(id);
    }

    @Override
    public Long getRecipeCommentPostId(Long id) {
        return commentRepository.getRecipeCommentPostId(id);
    }

    @Override
    public Long getIMadeCommentPostId(Long id) {
        return commentRepository.getIMadeCommentPostId(id);
    }

    @Override
    public Long getOtherCommentPostId(Long id) {
        return commentRepository.getOtherCommentPostId(id);
    }

    @Override
    public void updateComment(Long commentId, String comment_text, PostType postType) {
        commentRepository.updateComment(commentId, comment_text, postType);
    }

    @Override
    public void deleteComment(Long commentId, PostType postType) {
        commentRepository.deleteComment(commentId, postType);
    }

    @Override
    public GardeningComment getLatestGardeningComment(long postId) {
        return commentRepository.getLatestGardeningComment(postId);
    }

    @Override
    public void addCommentLike(Long id, PostType postType) {
        commentRepository.addCommentLike(id, postType);
    }

    @Override
    public void addCommentNotification(Long commentId, Long postId, Long actionUser, Long receiverUser, PostType postType, CommentAction commentAction) {
        commentRepository.addCommentNotification(commentId, postId, actionUser, receiverUser, postType, commentAction);
    }

    @Override
    public void deleteCommentNotification(Long commentId, Long postId, PostType postType) {
        commentRepository.deleteCommentNotification(commentId, postId, postType);
    }

    @Override
    public void removeCommentLike(Long id, PostType postType) {
        commentRepository.removeCommentLike(id, postType);
    }

    @Override
    public boolean userHasLikedComment(Long userId, Long commentId, String tableName) {
        return commentRepository.userHasLikedComment(userId, commentId, tableName);
    }

    @Override
    public List<LikedGardeningComment> getUserLikedGardeningComments(Long userId) {
        return commentRepository.getUserLikedGardeningComments(userId);
    }

    @Override
    public List<LikedRecipeComment> getUserLikedRecipeComments(Long userId) {
        return commentRepository.getUserLikedRecipeComments(userId);
    }

    @Override
    public List<LikedIMadeComment> getUserLikedIMadeComments(Long userId) {
        return commentRepository.getUserLikedIMadeComments(userId);
    }

    @Override
    public List<LikedOtherComment> getUserLikedOtherComments(Long userId) {
        return commentRepository.getUserLikedOtherComments(userId);
    }
}
