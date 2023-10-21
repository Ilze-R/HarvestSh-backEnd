package com.example.demo.repository.implementation;

import com.example.demo.domain.*;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.PostRepository;
import com.example.demo.rowmapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public GardeningPost create(Long userId, GardeningPost gardeningPost, MultipartFile image) {
                try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String postImageUrl = setPostImageUrl(gardeningPost.getTitle().trim());
            gardeningPost.setImg_url(postImageUrl);
            saveImage(gardeningPost.getTitle().trim(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", gardeningPost.getTitle());
            parameters.addValue("description", gardeningPost.getDescription());
           // log.info(gardeningPost.getDescription());
            parameters.addValue("tag", gardeningPost.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", gardeningPost.getView_count());
            parameters.addValue("img_url", gardeningPost.getImg_url());
            parameters.addValue("users_gardening_post_id", userId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_GARDENING_POST_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            gardeningPost.setId(generatedId);
            gardeningPost.setDate(currentDateTime);
            return gardeningPost;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public RecipePost create(Long userId, RecipePost recipePost, MultipartFile image) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String postImageUrl = setPostImageUrl(recipePost.getTitle().trim());
           recipePost.setImg_url(postImageUrl);
            saveImage(recipePost.getTitle().trim(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", recipePost.getTitle());
            parameters.addValue("description", recipePost.getDescription());
            parameters.addValue("tag", recipePost.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", recipePost.getView_count());
            parameters.addValue("img_url", recipePost.getImg_url());
            parameters.addValue("users_recipe_post_id", userId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_RECIPE_POST_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            recipePost.setId(generatedId);
            recipePost.setDate(currentDateTime);
            return recipePost;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public IMadePost create(Long userId, IMadePost iMadePost, MultipartFile image) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String postImageUrl = setPostImageUrl(iMadePost.getTitle().trim());
            iMadePost.setImg_url(postImageUrl);
            saveImage(iMadePost.getTitle().trim(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", iMadePost.getTitle());
            parameters.addValue("description", iMadePost.getDescription());
            parameters.addValue("tag", iMadePost.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", iMadePost.getView_count());
            parameters.addValue("img_url", iMadePost.getImg_url());
            parameters.addValue("users_i_made_post_id", userId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_I_MADE_POST_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            iMadePost.setId(generatedId);
            iMadePost.setDate(currentDateTime);
            return iMadePost;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public OtherPost create(Long userId, OtherPost otherPost, MultipartFile image) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String postImageUrl = setPostImageUrl(otherPost.getTitle().trim());
            otherPost.setImg_url(postImageUrl);
            saveImage(otherPost.getTitle().trim(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("title", otherPost.getTitle());
            parameters.addValue("description", otherPost.getDescription());
            parameters.addValue("tag", otherPost.getTag());
            parameters.addValue("likes", 0);
            parameters.addValue("view_count", otherPost.getView_count());
            parameters.addValue("img_url", otherPost.getImg_url());
            parameters.addValue("users_other_post_id", userId);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_OTHER_POST_QUERY, parameters, keyHolder);
            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            otherPost.setId(generatedId);
            otherPost.setDate(currentDateTime);
            return otherPost;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public void deleteGardeningPost(Long postId) {
        try {
            jdbc.update(DELETE_GARDENING_POST, of("postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred here. Please try again.");
        }
    }

    @Override
    public void deleteRecipePost(Long postId) {
        try {
            jdbc.update(DELETE_RECIPE_POST, of("postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadePost(Long postId) {
        try {
            jdbc.update(DELETE_I_MADE_POST, of("postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherPost(Long postId) {
        try {
            jdbc.update(DELETE_OTHER_POST, of("postId", postId));
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
    public RecipeComment addRecipeComment(Long userId, Long postId, RecipeComment recipeComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", recipeComment.getComment_text());
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
    public IMadeComment addIMadeComment(Long userId, Long postId, IMadeComment iMadeComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", iMadeComment.getComment_text());
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
    public OtherComment addOtherComment(Long userId, Long postId, OtherComment otherComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", otherComment.getComment_text());
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
    public void updateGardeningComment(Long commentId, String comment_text) {
            try {
                jdbc.update(UPDATE_GARDENING_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
            } catch (Exception exception) {
                throw new ApiException("An error occurred. Please try again.");
            }
    }

    @Override
    public void updateRecipeComment(Long commentId, String comment_text) {
        try {
            jdbc.update(UPDATE_RECIPE_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void updateIMadeComment(Long commentId, String comment_text) {
        try {
            jdbc.update(UPDATE_I_MADE_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void updateOtherComment(Long commentId, String comment_text) {
        try {
            jdbc.update(UPDATE_OTHER_COMMENT_BY_COMMENT_ID_QUERY, of("id", commentId, "comment_text", comment_text));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGardeningComment(Long commentId) {
        try {
            jdbc.update(DELETE_GARDENING_COMMENT, of("id", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteRecipeComment(Long commentId) {
        try {
            jdbc.update(DELETE_RECIPE_COMMENT, of("id", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadeComment(Long commentId) {
        try {
            jdbc.update(DELETE_I_MADE_COMMENT, of("id", commentId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherComment(Long commentId) {
        try {
            jdbc.update(DELETE_OTHER_COMMENT, of("id", commentId));
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
    public void addGardeningLike(Long id) {
        try {
           jdbc.update(UPDATE_PLUS_GARDENING_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGardeningLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_GARDENING_LIKES, of("id", id));
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
    public int getAllGardeningPostLikes(Long postId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_GARDENING_POST_LIKES, of("postId", postId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
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
            String sql = "SELECT gp.likes " +
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
    public void addGardeningCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_GARDENING_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGardeningCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_GARDENING_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllGardeningCommentLikes(Long commentId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_GARDENING_COMMENT_LIKES, of("commentId", commentId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
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
            String sql = "SELECT gc.likes " +
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
    public void addRecipeLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_RECIPE_LIKES, of("id", id));
            log.info("Executing SQL: {}", UPDATE_PLUS_RECIPE_LIKES);
            log.info("Parameter values: id = {}", id);
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteRecipeLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_RECIPE_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllRecipePostLikes(Long postId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_RECIPE_POST_LIKES, of("postId", postId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
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
            String sql = "SELECT gp.likes " +
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
    public void addRecipeCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_RECIPE_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred here. Please try again.");
        }
    }

    @Override
    public void deleteRecipeCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_RECIPE_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred there. Please try again.");
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
    public int getAllRecipeCommentLikes(Long commentId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_RECIPE_COMMENT_LIKES, of("commentId", commentId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
        } catch (Exception exception) {
            throw new ApiException("An error occurred test. Please try again.");
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
            String sql = "SELECT gc.likes " +
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
    public void addIMadeLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_I_MADE_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadeLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_I_MADE_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllIMadePostLikes(Long postId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_I_MADE_POST_LIKES, of("postId", postId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
        } catch (Exception exception) {
            throw new ApiException("An error occurred here. Please try again.");
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
            String sql = "SELECT gp.likes " +
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
    public void addIMadeCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_I_MADE_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteIMadeCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_I_MADE_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllIMadeCommentLikes(Long commentId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_I_MADE_COMMENT_LIKES, of("commentId", commentId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
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
            String sql = "SELECT gc.likes " +
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
    public void addOtherLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_OTHER_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_OTHER_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllOtherPostLikes(Long postId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_OTHER_POST_LIKES, of("postId", postId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
        } catch (Exception exception) {
            throw new ApiException("An error occurred here. Please try again.");
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
            String sql = "SELECT gp.likes " +
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
    public void addOtherCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_PLUS_OTHER_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteOtherCommentLike(Long id) {
        try {
            jdbc.update(UPDATE_MINUS_OTHER_COMMENT_LIKES, of("id", id));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
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
    public int getAllOtherCommentLikes(Long commentId) {
        try {
            Integer likes = jdbc.queryForObject(GET_ALL_OTHER_COMMENT_LIKES, of("commentId", commentId), Integer.class);
            return Objects.requireNonNullElse(likes, 0);
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
            String sql = "SELECT gc.likes " +
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
    public GardeningPost getGardeningPostById(long id) {
        try {
            return jdbc.queryForObject(SELECT_GARDENING_POST_BY_ID, of("id", id), gardeningPostRowMapper);
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("Post found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public RecipePost getRecipePostById(long id) {
        try {
            return jdbc.queryForObject(SELECT_RECIPE_POST_BY_ID, of("id", id), recipePostRowMapper);
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("Post found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public IMadePost getIMadePostById(long id) {
        try {
            return jdbc.queryForObject(SELECT_I_MADE_POST_BY_ID, of("id", id), iMadePostRowMapper);
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("Post found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public OtherPost getOtherPostById(long id) {
        try {
            return jdbc.queryForObject(SELECT_OTHER_POST_BY_ID, of("id", id), otherPostRowMapper);
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("Post found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public int getAllRecipePostCount() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM RecipePost", Integer.class);
        return count != null ? count : 0;
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
