package com.example.demo.repository.implementation;

import com.example.demo.domain.*;
import com.example.demo.dto.UserDTO;
import com.example.demo.enumeration.PostType;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.PostRepository;
import com.example.demo.rowmapper.*;
import com.example.demo.service.UserService;
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
    private final RecipePostRowMapper recipePostRowMapper;
    private final IMadePostRowMapper iMadePostRowMapper;
    private final OtherPostRowMapper otherPostRowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

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
    public void addPostLikeKeyTable(Long userId, Long postId, PostType postType) {
        try {
            String addQuery = getAddPostLikeQuery(postType);
            jdbc.update(addQuery, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deletePostLikeKeyTable(Long userId, Long postId, PostType postType) {
        try {
            String deleteQuery = getDeletePostLikeQuery(postType);
            jdbc.update(deleteQuery, of("userId", userId, "postId", postId));
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private String getAddPostLikeQuery(PostType postType) {
        return getPostString(postType, ADD_GARDENING_POST_LIKES_KEY_TABLE, ADD_RECIPE_POST_LIKES_KEY_TABLE, ADD_I_MADE_POST_LIKES_KEY_TABLE, ADD_OTHER_POST_LIKES_KEY_TABLE);
    }
    private String getDeletePostLikeQuery(PostType postType) {
        return getPostString(postType, DELETE_GARDENING_POST_LIKES_KEY_TABLE, DELETE_RECIPE_POST_LIKES_KEY_TABLE, DELETE_I_MADE_POST_LIKES_KEY_TABLE, DELETE_OTHER_POST_LIKES_KEY_TABLE);
    }

    private String getPostString(PostType postType, String GardeningTable, String RecipeTable, String IMadeTable, String OtherTable) {
        return switch (postType) {
            case GARDENING -> GardeningTable;
            case RECIPE -> RecipeTable;
            case I_MADE -> IMadeTable;
            case OTHER -> OtherTable;
            default -> throw new IllegalArgumentException("Unsupported comment type: " + postType);
        };
    }
    @Override
    public void addPostLike(Long id, PostType postType) {
        jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_PLUS_GARDENING_POST_LIKES, UPDATE_PLUS_RECIPE_POST_LIKES, UPDATE_PLUS_I_MADE_POST_LIKES, UPDATE_PLUS_OTHER_POST_LIKES);
    }

    @Override
    public void addPostLikeNotification(Long postId, Long actionUser, Long receiverUser, PostType postType) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        UserDTO actionUserObj = userService.getUserById(actionUser);
        String actionUsername = actionUserObj.getUsername();
        parameters.addValue("user_id", receiverUser);
        parameters.addValue("action_user_id", actionUser);
        parameters.addValue("message", actionUsername.substring(0,1).toUpperCase(Locale.ROOT) + actionUsername.substring(1).toLowerCase() + " liked Your post");
        parameters.addValue("is_read", false);
        parameters.addValue("created_at", Timestamp.valueOf(currentDateTime));
        parameters.addValue("forum_type", postType.toString());
        parameters.addValue("target", "POST");
        parameters.addValue("target_id", postId);
        jdbc.update(ADD_NOTIFICATION_ABOUT_POST_LIKE, parameters);
    }

    @Override
    public void deletePostLikeNotification(Long postId, PostType postType) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("forum_type", postType.name());
        parameters.addValue("target", "POST");
        parameters.addValue("target_id", postId);
        jdbc.update(DELETE_NOTIFICATION_ABOUT_POST_LIKE, parameters);
    }


    @Override
    public void removePostLike(Long id, PostType postType) {
            jdbcUpdateQueryWithIdParameter(id, postType, UPDATE_MINUS_GARDENING_POST_LIKES, UPDATE_MINUS_RECIPE_POST_LIKES, UPDATE_MINUS_I_MADE_POST_LIKES, UPDATE_MINUS_OTHER_POST_LIKES);
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
    public boolean userHasLikedPost(Long userId, Long postId, String tableName) {
        try {
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE user_id = :userId AND post_id = :postId", tableName);
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
    public List<GardeningPost> getAllGardeningPosts(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT GardeningPost.*, Users.image_url AS user_image_url " +
                "FROM GardeningPost " +
                "INNER JOIN Users ON GardeningPost.users_gardening_post_id = Users.id " +
                "LIMIT ? OFFSET ?", gardeningPostRowMapper, pageSize, offset);
    }

    @Override
    public List<GardeningPost> getAllGardeningPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT GardeningPost.*, Users.image_url AS user_image_url, Users.username AS username, Users.id AS author_user_id " +
                "FROM GardeningPost " +
                "INNER JOIN Users ON GardeningPost.users_gardening_post_id = Users.id " +
                "ORDER BY GardeningPost.date DESC " +
                "LIMIT ? OFFSET ?", gardeningPostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<RecipePost> getAllRecipePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT RecipePost.*, Users.image_url AS user_image_url, Users.username AS username, Users.id AS author_user_id " +
                "FROM RecipePost " +
                "INNER JOIN Users ON RecipePost.users_recipe_post_id = Users.id " +
                "ORDER BY RecipePost.date DESC " +
                "LIMIT ? OFFSET ?", recipePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<IMadePost> getAllIMadePost(Pageable pageable) {
        return jdbcTemplate.query("SELECT IMadePost.*, Users.image_url AS user_image_url, Users.username AS username, Users.id AS author_user_id " +
                "FROM IMadePost " +
                "INNER JOIN Users ON IMadePost.users_i_made_post_id = Users.id " +
                "ORDER BY IMadePost.date DESC " +
                "LIMIT ? OFFSET ?", iMadePostRowMapper, pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<OtherPost> getAllOtherPost(Pageable pageable) {
        return jdbcTemplate.query("SELECT OtherPost.*, Users.image_url AS user_image_url, Users.username AS username, Users.id AS author_user_id " +
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
