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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            parameters.addValue("likes", gardeningPost.getLikes());
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
            parameters.addValue("likes", recipePost.getLikes());
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
            parameters.addValue("likes", iMadePost.getLikes());
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
            parameters.addValue("likes", otherPost.getLikes());
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
    public GardeningComment addGardeningComment(Long userId, Long postId, GardeningComment gardeningComment) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("comment_text", gardeningComment.getComment_text());
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
    public GardeningComment getGardeningCommentById(long id) {
        try {
            return jdbc.queryForObject(SELECT_GARDENING_COMMENT_BY_ID_QUERY, of("id", id), new GardeningCommentRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No comment found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }


    @Override
    public List<GardeningPost> getAllGardeningPosts() {
        return jdbcTemplate.query("SELECT * FROM GardeningPost", gardeningPostRowMapper);
    }

    @Override
    public List<GardeningPost> getAllGardeningPosts(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM GardeningPost limit ? offset ?", gardeningPostRowMapper, pageSize, offset);
    }

    @Override
    public List<GardeningPost> getAllGardeningPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM GardeningPost limit ? offset ?", gardeningPostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<RecipePost> getAllRecipePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM RecipePost limit ? offset ?", recipePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<IMadePost> getAllIMadePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM IMadePost limit ? offset ?", iMadePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<OtherPost> getAllOtherPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM OtherPost limit ? offset ?", otherPostRowMapper, pageable.getPageSize(), pageable.getOffset());
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
