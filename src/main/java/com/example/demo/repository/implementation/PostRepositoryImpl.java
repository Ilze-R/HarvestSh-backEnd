package com.example.demo.repository.implementation;

import com.example.demo.domain.*;
import com.example.demo.enumeration.PostType;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.PostRepository;
import com.example.demo.rowmapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.demo.query.PostQuery.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Map.of;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final GardeningPostRowMapper gardeningPostRowMapper;
    private final LikedGardeningPostRowMapper likedGardeningPostRowMapper;
    private final LikedRecipePostRowMapper likedRecipePostRowMapper;
    private final LikedIMadePostRowMapper likedIMadePostRowMapper;
    private final LikedOtherPostRowMapper likedOtherPostRowMapper;
    private final LikedGardeningCommRowMapper likedGardeningCommRowMapper;
    private final LikedRecipeCommRowMapper likedRecipeCommRowMapper;
    private final LikedIMadeCommRowMapper likedIMadeCommRowMapper;
    private final LikedOtherCommRowMapper likedOtherCommRowMapper;
    private final GardeningCommentRowMapper gardeningCommentRowMapper;
    private final RecipeCommentRowMapper recipeCommentRowMapper;
    private final IMadeCommentRowMapper iMadeCommentRowMapper;
    private final OtherCommentRowMapper otherCommentRowMapper;
    private final RecipePostRowMapper recipePostRowMapper;
    private final IMadePostRowMapper iMadePostRowMapper;
    private final OtherPostRowMapper otherPostRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public <T extends Post> T create(Long userId, T post, MultipartFile image) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String postImageUrl = setPostImageUrl(post.getTitle().trim());
            post.setImg_url(postImageUrl);
            saveImage(post.getTitle().trim(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", post.getTitle());
            parameters.addValue("description", post.getDescription());
            parameters.addValue("tag", post.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", post.getView_count());
            parameters.addValue("img_url", post.getImg_url());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            if (post instanceof GardeningPost) {
                parameters.addValue("users_gardening_post_id", userId);
                jdbc.update(INSERT_GARDENING_POST_QUERY, parameters, keyHolder);
            } else if (post instanceof RecipePost) {
                parameters.addValue("users_recipe_post_id", userId);
                jdbc.update(INSERT_RECIPE_POST_QUERY, parameters, keyHolder);
            } else if (post instanceof IMadePost) {
                parameters.addValue("users_i_made_post_id", userId);
                jdbc.update(INSERT_I_MADE_POST_QUERY, parameters, keyHolder);
            } else if (post instanceof OtherPost) {
                parameters.addValue("users_other_post_id", userId);
                jdbc.update(INSERT_OTHER_POST_QUERY, parameters, keyHolder);
            }
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            post.setId(generatedId);
            post.setDate(currentDateTime);
            return post;
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public GardeningPost createGardeningPostNoPhoto(long userId, GardeningPost gardeningPost) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", gardeningPost.getTitle());
            parameters.addValue("description", gardeningPost.getDescription());
            parameters.addValue("tag", gardeningPost.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", gardeningPost.getView_count());
            parameters.addValue("users_gardening_post_id", userId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_GARDENING_POST_NO_IMAGE_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            gardeningPost.setId(generatedId);
            gardeningPost.setDate(currentDateTime);
            return gardeningPost;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public void deletePost(Long postId, PostType postType) {
        try {
            switch (postType) {
                case GARDENING -> jdbc.update(DELETE_GARDENING_POST, of("postId", postId));
                case RECIPE -> jdbc.update(DELETE_RECIPE_POST, of("postId", postId));
                case I_MADE -> jdbc.update(DELETE_I_MADE_POST, of("postId", postId));
                case OTHER -> jdbc.update(DELETE_OTHER_POST, of("postId", postId));
                default -> throw new IllegalArgumentException("Unsupported post type: " + postType);
            }
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts() {
        return null;
    }

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
    public void deleteComment(Long commentId, PostType postType) {
        jdbcUpdateQueryWithIdParameter(commentId, postType, DELETE_GARDENING_COMMENT, DELETE_RECIPE_COMMENT, DELETE_I_MADE_COMMENT, DELETE_OTHER_COMMENT);
    }

    @Override
    public void addPostLike(Long id, PostType postType) {
        jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_PLUS_GARDENING_LIKES, UPDATE_PLUS_RECIPE_LIKES, UPDATE_PLUS_I_MADE_LIKES, UPDATE_PLUS_OTHER_LIKES);
    }

    @Override
    public void removePostLike(Long id, PostType postType) {
            jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_MINUS_GARDENING_LIKES, UPDATE_MINUS_RECIPE_LIKES, UPDATE_MINUS_I_MADE_LIKES, UPDATE_MINUS_OTHER_LIKES);
    }

    @Override
    public void addCommentLike(Long id, PostType postType) {
        jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_PLUS_GARDENING_COMMENT_LIKES, UPDATE_PLUS_RECIPE_COMMENT_LIKES, UPDATE_PLUS_I_MADE_COMMENT_LIKES, UPDATE_PLUS_OTHER_COMMENT_LIKES);
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
    public void addGardeningPostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(ADD_GARDENING_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGardeningPostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(DELETE_GARDENING_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedGardeningPost(Long userId, Long postId) {
        try {
            String sql = "SELECT COUNT(*) FROM GardeningPostLikes WHERE user_id = :userId AND post_id = :postId";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("postId", postId);
            int likeCount = jdbc.queryForObject(sql, params, Integer.class);
            return likeCount > 0;
        } catch (Exception exception) {
            throw new ApiException("An error occurred while checking if the user has liked the post.");
        }
    }

    @Override
    public List<LikedGardeningPost> getUserLikedGardeningPosts(Long userId) {
        try {
            String sql = "SELECT gp.id, gp.likes " +
                    "FROM GardeningPostLikes pl " +
                    "INNER JOIN GardeningPost gp ON pl.post_id = gp.id " +
                    "WHERE pl.user_id = :userId";

            List<LikedGardeningPost> likedPosts = jdbc.query(sql, of("userId", userId), likedGardeningPostRowMapper);
            return likedPosts;
        } catch (Exception exception) {
            log.error("Error retrieving liked posts for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked posts.");
        }
    }

    @Override
    public void addGardeningCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(ADD_GARDENING_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGardeningCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(DELETE_GARDENING_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedGardeningComment(Long userId, Long commentId) {
        try {
            String sql = "SELECT COUNT(*) FROM GardeningCommentLikes WHERE user_id = :userId AND comment_id = :commentId";
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
    public void addRecipePostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(ADD_RECIPE_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteRecipePostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(DELETE_RECIPE_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedRecipePost(Long userId, Long postId) {
        try {
            String sql = "SELECT COUNT(*) FROM RecipePostLikes WHERE user_id = :userId AND post_id = :postId";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("postId", postId);
            int likeCount = jdbc.queryForObject(sql, params, Integer.class);
            return likeCount > 0;
        } catch (Exception exception) {
            throw new ApiException("An error occurred while checking if the user has liked the post.");
        }
    }

    @Override
    public List<LikedRecipePost> getUserLikedRecipePosts(Long userId) {
        try {
            String sql = "SELECT gp.id, gp.likes " +
                    "FROM RecipePostLikes pl " +
                    "INNER JOIN RecipePost gp ON pl.post_id = gp.id " +
                    "WHERE pl.user_id = :userId";

            List<LikedRecipePost> likedPosts = jdbc.query(sql, of("userId", userId), likedRecipePostRowMapper);
            return likedPosts;
        } catch (Exception exception) {
            log.error("Error retrieving liked posts for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked posts.");
        }
    }
    @Override
    public void addRecipeCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(ADD_RECIPE_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred maybe. Please try again.");
        }
    }

    @Override
    public void deleteRecipeCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(DELETE_RECIPE_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred some. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedRecipeComment(Long userId, Long commentId) {
        try {
            String sql = "SELECT COUNT(*) FROM RecipeCommentLikes WHERE user_id = :userId AND comment_id = :commentId";
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
    public void addIMadePostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(ADD_I_MADE_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadePostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(DELETE_I_MADE_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedIMadePost(Long userId, Long postId) {
        try {
            String sql = "SELECT COUNT(*) FROM IMadePostLikes WHERE user_id = :userId AND post_id = :postId";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("postId", postId);
            int likeCount = jdbc.queryForObject(sql, params, Integer.class);
            return likeCount > 0;
        } catch (Exception exception) {
            throw new ApiException("An error occurred while checking if the user has liked the post.");
        }
    }

    @Override
    public List<LikedIMadePost> getUserLikedIMadePosts(Long userId) {
        try {
            String sql = "SELECT gp.id, gp.likes " +
                    "FROM IMadePostLikes pl " +
                    "INNER JOIN IMadePost gp ON pl.post_id = gp.id " +
                    "WHERE pl.user_id = :userId";

            List<LikedIMadePost> likedPosts = jdbc.query(sql, of("userId", userId), likedIMadePostRowMapper);
            return likedPosts;
        } catch (Exception exception) {
            log.error("Error retrieving liked posts for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked posts.");
        }
    }

    @Override
    public void addIMadeCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(ADD_I_MADE_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadeCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(DELETE_I_MADE_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedIMadeComment(Long userId, Long commentId) {
        try {
            String sql = "SELECT COUNT(*) FROM IMadeCommentLikes WHERE user_id = :userId AND comment_id = :commentId";
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
    public void addOtherPostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(ADD_OTHER_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherPostLikeKeyTable(Long userId, Long postId) {
        try {
            jdbc.update(DELETE_OTHER_POST_LIKES_KEY_TABLE, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedOtherPost(Long userId, Long postId) {
        try {
            String sql = "SELECT COUNT(*) FROM OtherPostLikes WHERE user_id = :userId AND post_id = :postId";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("userId", userId)
                    .addValue("postId", postId);
            int likeCount = jdbc.queryForObject(sql, params, Integer.class);
            return likeCount > 0;
        } catch (Exception exception) {
            throw new ApiException("An error occurred while checking if the user has liked the post.");
        }
    }

    @Override
    public List<LikedOtherPost> getUserLikedOtherPosts(Long userId) {
        try {
            String sql = "SELECT gp.id, gp.likes " +
                    "FROM OtherPostLikes pl " +
                    "INNER JOIN OtherPost gp ON pl.post_id = gp.id " +
                    "WHERE pl.user_id = :userId";

            List<LikedOtherPost> likedPosts = jdbc.query(sql, of("userId", userId), likedOtherPostRowMapper);
            return likedPosts;
        } catch (Exception exception) {
            log.error("Error retrieving liked posts for user " + userId, exception);
            throw new ApiException("An error occurred while retrieving liked posts.");
        }
    }

    @Override
    public void addOtherCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(ADD_OTHER_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherCommentLikeKeyTable(Long userId, Long commentId) {
        try {
            jdbc.update(DELETE_OTHER_COMMENT_LIKES_KEY_TABLE, of("userId", userId, "commentId", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public boolean userHasLikedOtherComment(Long userId, Long commentId) {
        try {
            String sql = "SELECT COUNT(*) FROM OtherCommentLikes WHERE user_id = :userId AND comment_id = :commentId";
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

    @Override
    public List<GardeningPost> getAllGardeningPosts(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT GardeningPost.*, Users.image_url AS user_image_url " +
                "FROM GardeningPost " +
                "INNER JOIN Users ON GardeningPost.users_gardening_post_id = Users.id " +
                "LIMIT ? OFFSET ?", gardeningPostRowMapper, pageSize, offset);
    }

    @Override
    public List<GardeningPost> getAllGardeningPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT GardeningPost.*, Users.image_url AS user_image_url " +
                "FROM GardeningPost " +
                "INNER JOIN Users ON GardeningPost.users_gardening_post_id = Users.id " +
                "ORDER BY GardeningPost.date DESC " +
                "LIMIT ? OFFSET ?", gardeningPostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }


    @Override
    public List<RecipePost> getAllRecipePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT RecipePost.*, Users.image_url AS user_image_url " +
                "FROM RecipePost " +
                "INNER JOIN Users ON RecipePost.users_recipe_post_id = Users.id " +
                "ORDER BY RecipePost.date DESC " +
                "LIMIT ? OFFSET ?", recipePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<IMadePost> getAllIMadePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT IMadePost.*, Users.image_url AS user_image_url " +
                "FROM IMadePost " +
                "INNER JOIN Users ON IMadePost.users_i_made_post_id = Users.id " +
                "ORDER BY IMadePost.date DESC " +
                "LIMIT ? OFFSET ?", iMadePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<OtherPost> getAllOtherPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT OtherPost.*, Users.image_url AS user_image_url " +
                "FROM OtherPost " +
                "INNER JOIN Users ON OtherPost.users_other_post_id = Users.id " +
                "ORDER BY OtherPost.date DESC " +
                "LIMIT ? OFFSET ?", otherPostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public int getAllRecipePostCount() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RecipePost", Integer.class);
        return count != null ? count : 0;
    }

    @Override
    public GardeningPost getGardeningPostById(long id) {
        return getPostById(id, PostType.GARDENING, SELECT_GARDENING_POST_BY_ID, gardeningPostRowMapper);
    }

    @Override
    public RecipePost getRecipePostById(long id) {
        return getPostById(id, PostType.RECIPE, SELECT_RECIPE_POST_BY_ID, recipePostRowMapper);
    }

    @Override
    public IMadePost getIMadePostById(long id) {
        return getPostById(id, PostType.I_MADE, SELECT_I_MADE_POST_BY_ID, iMadePostRowMapper);
    }

    @Override
    public OtherPost getOtherPostById(long id) {
        return getPostById(id, PostType.OTHER, SELECT_OTHER_POST_BY_ID, otherPostRowMapper);
    }

    public <T extends Post> T getPostById(long id, PostType postType, String selectQuery, RowMapper<T> rowMapper) {
        try {
            switch (postType) {
                case GARDENING, RECIPE, I_MADE, OTHER -> {
                    return jdbc.queryForObject(selectQuery, of("id", id), rowMapper);
                }
                default -> throw new IllegalArgumentException("Unsupported post type: " + postType);
            }
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("Post found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    private String setPostImageUrl(String type) {
        return fromCurrentContextPath().path("/user/image/" + type + ".png").toUriString();
    }

    private void saveImage(String email, MultipartFile image) {
        Path fileStorageLocation = Paths.get(System.getProperty("user.home") + "/Downloads/images/").toAbsolutePath().normalize();
        if(!Files.exists(fileStorageLocation)){
            try {
                Files.createDirectories(fileStorageLocation);
            }catch (Exception exception){
                log.error(exception.getMessage());
                throw new ApiException("Unable to create directories to save image");
            }
            log.info("Created directories: {}", fileStorageLocation);
        }
        try{
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(email + ".png"), REPLACE_EXISTING);
        } catch (IOException exception){
            throw  new ApiException(exception.getMessage());
        }
        log.info("File saved in: {} folder", fileStorageLocation);
    }
}
