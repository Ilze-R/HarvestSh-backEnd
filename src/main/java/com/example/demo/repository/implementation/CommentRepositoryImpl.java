package com.example.demo.repository.implementation;

import com.example.demo.domain.*;
import com.example.demo.dto.UserDTO;
import com.example.demo.enumeration.PostType;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.rowmapper.*;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.demo.query.CommentQuery.*;
import static com.example.demo.query.PostQuery.DELETE_NOTIFICATION_ABOUT_POST_LIKE;
import static java.util.Map.of;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final LikedGardeningCommRowMapper likedGardeningCommRowMapper;
    private final LikedRecipeCommRowMapper likedRecipeCommRowMapper;
    private final LikedIMadeCommRowMapper likedIMadeCommRowMapper;
    private final LikedOtherCommRowMapper likedOtherCommRowMapper;
    private final GardeningCommentRowMapper gardeningCommentRowMapper;
    private final RecipeCommentRowMapper recipeCommentRowMapper;
    private final IMadeCommentRowMapper iMadeCommentRowMapper;
    private final OtherCommentRowMapper otherCommentRowMapper;
    private final UserService userService;

    @Override
    public GardeningComment addGardeningComment(Long userId, Long postId, GardeningComment gardeningComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", gardeningComment.getComment_text());
            parameters.addValue("reply_username", gardeningComment.getReply_username());
            parameters.addValue("likes", 0);
            parameters.addValue("parent_comment_id", gardeningComment.getParent_comment_id());
            parameters.addValue("comment_user_id",userId);
            parameters.addValue("comment_gardening_post_id", postId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_GARDENING_COMMENT_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            gardeningComment.setId(generatedId);
            gardeningComment.setDate(currentDateTime);
            return gardeningComment;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public RecipeComment addRecipeComment(Long userId, Long postId, RecipeComment recipeComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", recipeComment.getComment_text());
            parameters.addValue("reply_username", recipeComment.getReply_username());
            parameters.addValue("likes", 0);
            parameters.addValue("parent_comment_id", recipeComment.getParent_comment_id());
            parameters.addValue("comment_user_id",userId);
            parameters.addValue("comment_recipe_post_id", postId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_RECIPE_COMMENT_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            recipeComment.setId(generatedId);
            recipeComment.setDate(currentDateTime);
            return recipeComment;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }
    @Override
    public IMadeComment addIMadeComment(Long userId, Long postId, IMadeComment iMadeComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", iMadeComment.getComment_text());
            parameters.addValue("reply_username", iMadeComment.getReply_username());
            parameters.addValue("likes", 0);
            parameters.addValue("parent_comment_id", iMadeComment.getParent_comment_id());
            parameters.addValue("comment_user_id",userId);
            parameters.addValue("comment_i_made_post_id", postId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_I_MADE_COMMENT_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            iMadeComment.setId(generatedId);
            iMadeComment.setDate(currentDateTime);
            return iMadeComment;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }
    @Override
    public OtherComment addOtherComment(Long userId, Long postId, OtherComment otherComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", otherComment.getComment_text());
            parameters.addValue("reply_username", otherComment.getReply_username());
            parameters.addValue("likes", 0);
            parameters.addValue("parent_comment_id", otherComment.getParent_comment_id());
            parameters.addValue("comment_user_id",userId);
            parameters.addValue("comment_other_post_id", postId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_OTHER_COMMENT_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            otherComment.setId(generatedId);
            otherComment.setDate(currentDateTime);
            return otherComment;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public List<GardeningComment> getAllGardeningCommentsByPostId(Long postId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comment_gardening_post_id", postId);
            return jdbc.query(SELECT_ALL_GARDENING_COMMENTS_BY_POST_ID, params, gardeningCommentRowMapper);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public List<RecipeComment> getAllRecipeCommentsByPostId(Long postId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comment_recipe_post_id", postId);
            return jdbc.query(SELECT_ALL_RECIPE_COMMENTS_BY_POST_ID, params, recipeCommentRowMapper);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public List<IMadeComment> getAllIMadeCommentsByPostId(Long postId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comment_i_made_post_id", postId);
            return jdbc.query(SELECT_ALL_I_MADE_COMMENTS_BY_POST_ID, params, iMadeCommentRowMapper);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public List<OtherComment> getAllOtherCommentsByPostId(Long postId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comment_other_post_id", postId);
            return jdbc.query(SELECT_ALL_OTHER_COMMENTS_BY_POST_ID, params, otherCommentRowMapper);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public void updateComment(Long commentId, String comment_text, PostType postType) {
        try {
            switch (postType) {
                case GARDENING -> jdbc.update(UPDATE_GARDENING_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
                case RECIPE -> jdbc.update(UPDATE_RECIPE_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
                case I_MADE -> jdbc.update(UPDATE_I_MADE_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
                case OTHER -> jdbc.update(UPDATE_OTHER_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
                default -> throw new IllegalArgumentException("Unsupported post type: " + postType);
            }
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public GardeningComment getLatestGardeningComment(Long postId) {
        try {
            return jdbc.queryForObject(SELECT_LATEST_COMMENT_FROM_GARDENING_POST, of("postId", postId), gardeningCommentRowMapper);
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("Comments were not fount: " + postId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void addCommentLikeKeyTable(Long userId, Long commentId, PostType postType) {
        try {
            String addQuery = getAddCommentLikeQuery(postType);
            jdbc.update(addQuery, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteCommentLikeKeyTable(Long userId, Long commentId, PostType postType) {
        try {
            String deleteQuery = getDeleteCommentLikeQuery(postType);
            jdbc.update(deleteQuery, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private String getAddCommentLikeQuery(PostType postType) {
        return getCommentsString(postType, ADD_GARDENING_COMMENT_LIKES_KEY_TABLE, ADD_RECIPE_COMMENT_LIKES_KEY_TABLE, ADD_I_MADE_COMMENT_LIKES_KEY_TABLE, ADD_OTHER_COMMENT_LIKES_KEY_TABLE);
    }
    private String getDeleteCommentLikeQuery(PostType postType) {
        return getCommentsString(postType, DELETE_GARDENING_COMMENT_LIKES_KEY_TABLE, DELETE_RECIPE_COMMENT_LIKES_KEY_TABLE, DELETE_I_MADE_COMMENT_LIKES_KEY_TABLE, DELETE_OTHER_COMMENT_LIKES_KEY_TABLE);
    }
    private String getCommentsString(PostType postType, String GardeningTable, String RecipeTable, String IMadeTable, String OtherTable) {
        return switch (postType) {
            case GARDENING -> GardeningTable;
            case RECIPE -> RecipeTable;
            case I_MADE -> IMadeTable;
            case OTHER -> OtherTable;
            default -> throw new IllegalArgumentException("Unsupported comment type: " + postType);
        };
    }

    @Override
    public void deleteComment(Long commentId, PostType postType) {
        jdbcUpdateQueryWithIdParameter(commentId, postType, DELETE_GARDENING_COMMENT, DELETE_RECIPE_COMMENT, DELETE_I_MADE_COMMENT, DELETE_OTHER_COMMENT);
    }
    @Override
    public void addCommentLike(Long id, PostType postType) {
        jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_PLUS_GARDENING_COMMENT_LIKES, UPDATE_PLUS_RECIPE_COMMENT_LIKES, UPDATE_PLUS_I_MADE_COMMENT_LIKES, UPDATE_PLUS_OTHER_COMMENT_LIKES);
    }

    @Override
    public void addCommentLikeNotification(Long commentId, Long postId, Long actionUser, Long receiverUser, PostType postType) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        UserDTO actionUserObj = userService.getUserById(actionUser);
        String actionUsername = actionUserObj.getUsername();
        parameters.addValue("user_id", receiverUser);
        parameters.addValue("action_user_id", actionUser);
        parameters.addValue("message", actionUsername.substring(0,1).toUpperCase(Locale.ROOT) + actionUsername.substring(1).toLowerCase() + " liked Your comment");
        parameters.addValue("is_read", false);
        parameters.addValue("created_at", Timestamp.valueOf(currentDateTime));
        parameters.addValue("forum_type", postType.toString());
        parameters.addValue("target", "COMMENT");
        parameters.addValue("target_id", commentId);
        parameters.addValue("post_id", postId);
        jdbc.update(ADD_NOTIFICATION_ABOUT_COMMENT_LIKE, parameters);
    }

//            parameters.addValue("forum_type", postType.name());
//        parameters.addValue("target", "POST");
//        parameters.addValue("target_id", postId);
    @Override
    public void deleteCommentLikeNotification(Long commentId, Long postId, PostType postType) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("forum_type", postType.name());
        parameters.addValue("target", "COMMENT");
        parameters.addValue("target_id", commentId);
        parameters.addValue("post_id", postId);
        jdbc.update(DELETE_NOTIFICATION_ABOUT_COMMENT_LIKE, parameters);
    }

    @Override
    public void removeCommentLike(Long id, PostType postType) {
        jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_MINUS_GARDENING_COMMENT_LIKES, UPDATE_MINUS_RECIPE_COMMENT_LIKES, UPDATE_MINUS_I_MADE_COMMENT_LIKES, UPDATE_MINUS_OTHER_COMMENT_LIKES);
    }

    private void jdbcUpdateQueryWithIdParameter(Long id, PostType postType, String updateGardening, String updateRecipe, String updateIMade, String updateOther) {
        try {
            switch (postType) {
                case GARDENING -> jdbc.update(updateGardening, of("id", id));
                case RECIPE -> jdbc.update(updateRecipe, of("id", id));
                case I_MADE -> jdbc.update(updateIMade, of("id", id));
                case OTHER -> jdbc.update(updateOther, of("id", id));
                default -> throw new IllegalArgumentException("Unsupported post type: " + postType);
            }
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    @Override
    public boolean userHasLikedComment(Long userId, Long commentId, String tableName) {
        try {
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE user_id = :userId AND comment_id = :commentId", tableName);
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("commentId", commentId);
            int likeCount = jdbc.queryForObject(sql, params, Integer.class);
            return likeCount > 0;
        } catch (Exception exception) {
            throw new ApiException("An error occurred while checking if the user has liked the comment.");
        }
    }
    @Override
    public List<LikedGardeningComment> getUserLikedGardeningComments(Long userId) {
        try {
            String sql = "SELECT gc.id, gc.likes " +
                    "FROM GardeningCommentLikes cl " +
                    "INNER JOIN GardeningComment gc ON cl.comment_id = gc.id " +
                    "WHERE cl.user_id = :userId";
            List<LikedGardeningComment> likedComments = jdbc.query(sql, of("userId", userId), likedGardeningCommRowMapper);
            return likedComments;
        } catch (Exception exception) {
            log.error("Error retrieving liked comments for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked comments.");
        }
    }

    @Override
    public List<LikedRecipeComment> getUserLikedRecipeComments(Long userId) {
        try {
            String sql = "SELECT gc.id, gc.likes " +
                    "FROM RecipeCommentLikes cl " +
                    "INNER JOIN RecipeComment gc ON cl.comment_id = gc.id " +
                    "WHERE cl.user_id = :userId";
            List<LikedRecipeComment> likedComments = jdbc.query(sql, of("userId", userId), likedRecipeCommRowMapper);
            return likedComments;
        } catch (Exception exception) {
            log.error("Error retrieving liked comments for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked comments.");
        }
    }

    @Override
    public List<LikedIMadeComment> getUserLikedIMadeComments(Long userId) {
        try {
            String sql = "SELECT gc.id, gc.likes " +
                    "FROM IMadeCommentLikes cl " +
                    "INNER JOIN IMadeComment gc ON cl.comment_id = gc.id " +
                    "WHERE cl.user_id = :userId";
            List<LikedIMadeComment> likedComments = jdbc.query(sql, of("userId", userId), likedIMadeCommRowMapper);
            return likedComments;
        } catch (Exception exception) {
            log.error("Error retrieving liked comments for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked comments.");
        }
    }

    @Override
    public List<LikedOtherComment> getUserLikedOtherComments(Long userId) {
        try {
            String sql = "SELECT gc.id, gc.likes " +
                    "FROM OtherCommentLikes cl " +
                    "INNER JOIN OtherComment gc ON cl.comment_id = gc.id " +
                    "WHERE cl.user_id = :userId";
            List<LikedOtherComment> likedComments = jdbc.query(sql, of("userId", userId), likedOtherCommRowMapper);
            return likedComments;
        } catch (Exception exception) {
            log.error("Error retrieving liked comments for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked comments.");
        }
    }
}
