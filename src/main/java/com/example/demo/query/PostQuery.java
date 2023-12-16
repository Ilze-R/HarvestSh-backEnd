package com.example.demo.query;

public class PostQuery {
    public static final String INSERT_GARDENING_POST_QUERY = "INSERT INTO GardeningPost(date, title, description, tag, likes, view_count," +
            " img_url, users_gardening_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_gardening_post_id)";

    public static final String INSERT_GARDENING_POST_NO_IMAGE_QUERY = "INSERT INTO GardeningPost(date, title, description, tag, likes, view_count," +
            " users_gardening_post_id)" +
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :users_gardening_post_id)";

    public static final String INSERT_RECIPE_POST_QUERY = "INSERT INTO RecipePost(date, title, description, tag, likes, view_count, img_url, users_recipe_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_recipe_post_id)";

    public static final String INSERT_I_MADE_POST_QUERY = "INSERT INTO IMadePost(date, title, description, tag, likes, view_count, img_url, users_i_made_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_i_made_post_id)";

    public static final String INSERT_OTHER_POST_QUERY = "INSERT INTO OtherPost(date, title, description, tag, likes, view_count, img_url, users_other_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_other_post_id)";

    public static final String DELETE_GARDENING_POST = """
            BEGIN;

            DELETE FROM GardeningCommentLikes WHERE comment_id IN (SELECT id FROM GardeningComment WHERE comment_gardening_post_id = :postId);

            DELETE FROM GardeningComment WHERE comment_gardening_post_id = :postId;

            DELETE FROM GardeningPostLikes WHERE post_id = :postId;

            DELETE FROM GardeningPost WHERE id = :postId;

            COMMIT;""";

    public static final String DELETE_RECIPE_POST = """
            BEGIN;

            DELETE FROM RecipeCommentLikes WHERE comment_id IN (SELECT id FROM RecipeComment WHERE comment_recipe_post_id = :postId);

            DELETE FROM RecipeComment WHERE comment_recipe_post_id = :postId;

            DELETE FROM RecipePostLikes WHERE post_id = :postId;

            DELETE FROM RecipePost WHERE id = :postId;

            COMMIT;""";
    public static final String DELETE_I_MADE_POST = """
            BEGIN;

            DELETE FROM IMadeCommentLikes WHERE comment_id IN (SELECT id FROM IMadeComment WHERE comment_i_made_post_id = :postId);

            DELETE FROM IMadeComment WHERE comment_i_made_post_id = :postId;

            DELETE FROM IMadePostLikes WHERE post_id = :postId;

            DELETE FROM IMadePost WHERE id = :postId;

            COMMIT;""";
    public static final String DELETE_OTHER_POST = """
            BEGIN;

            DELETE FROM OtherCommentLikes WHERE comment_id IN (SELECT id FROM OtherComment WHERE comment_other_post_id = :postId);

            DELETE FROM OtherComment WHERE comment_other_post_id = :postId;

            DELETE FROM OtherPostLikes WHERE post_id = :postId;

            DELETE FROM OtherPost WHERE id = :postId;

            COMMIT;""";
    public static final String SELECT_GARDENING_POST_USER_BY_ID = "SELECT users_gardening_post_id FROM GardeningPost WHERE id = :id";
    public static final String SELECT_RECIPE_POST_USER_BY_ID = "SELECT users_recipe_post_id FROM RecipePost WHERE id = :id";
    public static final String SELECT_I_MADE_POST_USER_BY_ID = "SELECT users_i_made_post_id FROM IMadePost WHERE id = :id";
    public static final String SELECT_OTHER_POST_USER_BY_ID = "SELECT users_other_post_id FROM OtherPost WHERE id = :id";
    public static  final String UPDATE_PLUS_GARDENING_POST_LIKES = "UPDATE GardeningPost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_RECIPE_POST_LIKES = "UPDATE RecipePost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_I_MADE_POST_LIKES = "UPDATE IMadePost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_OTHER_POST_LIKES = "UPDATE OtherPost SET likes = likes + 1 WHERE id = :id";
    public static final String ADD_NOTIFICATION_ABOUT_POST_LIKE = "INSERT INTO Notifications (user_id, action_user_id, message, is_read, created_at, forum_type, target, target_id) VALUES (:user_id, :action_user_id, :message, :is_read, :created_at, :forum_type, :target, :target_id)";
    public static final String DELETE_NOTIFICATION_ABOUT_POST_LIKE = "DELETE FROM Notifications WHERE forum_type = :forum_type AND target = :target AND target_id = :target_id";
    public static  final String UPDATE_MINUS_GARDENING_POST_LIKES = "UPDATE GardeningPost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_RECIPE_POST_LIKES = "UPDATE RecipePost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_I_MADE_POST_LIKES = "UPDATE IMadePost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_OTHER_POST_LIKES = "UPDATE OtherPost SET likes = likes - 1 WHERE id = :id";
    public static final String ADD_GARDENING_POST_LIKES_KEY_TABLE = "INSERT INTO GardeningPostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_RECIPE_POST_LIKES_KEY_TABLE = "INSERT INTO RecipePostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_I_MADE_POST_LIKES_KEY_TABLE = "INSERT INTO IMadePostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_OTHER_POST_LIKES_KEY_TABLE = "INSERT INTO OtherPostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String DELETE_GARDENING_POST_LIKES_KEY_TABLE = "DELETE FROM GardeningPostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String DELETE_RECIPE_POST_LIKES_KEY_TABLE = "DELETE FROM RecipePostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String DELETE_I_MADE_POST_LIKES_KEY_TABLE = "DELETE FROM IMadePostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String DELETE_OTHER_POST_LIKES_KEY_TABLE = "DELETE FROM OtherPostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String GET_ALL_GARDENING_POST_LIKES = "SELECT likes FROM GardeningPost WHERE id = :postId";
    public static final String GET_ALL_RECIPE_POST_LIKES = "SELECT likes FROM RecipePost WHERE id = :postId";
    public static final String GET_ALL_I_MADE_POST_LIKES = "SELECT likes FROM IMadePost WHERE id = :postId";
    public static final String GET_ALL_OTHER_POST_LIKES = "SELECT likes FROM OtherPost WHERE id = :postId";
    public static final String SELECT_ALL_POSTS_LIKED_BY_USER = "SELECT post_id FROM GardeningPostLikes WHERE user_id = :userId;";
}
