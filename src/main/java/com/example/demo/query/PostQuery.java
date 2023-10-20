package com.example.demo.query;

import com.example.demo.domain.GardeningPost;

public class PostQuery {
    public static final String INSERT_GARDENING_POST_QUERY = "INSERT INTO GardeningPost(date, title, description, tag, likes, view_count," +
            " img_url, users_gardening_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_gardening_post_id)";

    public static final String INSERT_RECIPE_POST_QUERY = "INSERT INTO RecipePost(date, title, description, tag, likes, view_count, img_url, users_recipe_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_recipe_post_id)";

    public static final String INSERT_I_MADE_POST_QUERY = "INSERT INTO IMadePost(date, title, description, tag, likes, view_count, img_url, users_i_made_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_i_made_post_id)";

    public static final String INSERT_OTHER_POST_QUERY = "INSERT INTO OtherPost(date, title, description, tag, likes, view_count, img_url, users_other_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_other_post_id)";

    public static final String DELETE_GARDENING_POST = "BEGIN;\n" +
            "\n" +
            "DELETE FROM GardeningCommentLikes WHERE comment_id IN (SELECT id FROM GardeningComment WHERE comment_gardening_post_id = :postId);\n" +
            "\n" +
            "DELETE FROM GardeningComment WHERE comment_gardening_post_id = :postId;\n" +
            "\n" +
            "DELETE FROM GardeningPostLikes WHERE post_id = :postId;\n" +
            "\n" +
            "DELETE FROM GardeningPost WHERE id = :postId;\n" +
            "\n" +
            "COMMIT;";

    public static final String DELETE_RECIPE_POST = "BEGIN;\n" +
            "\n" +
            "DELETE FROM RecipeCommentLikes WHERE comment_id IN (SELECT id FROM RecipeComment WHERE comment_recipe_post_id = :postId);\n" +
            "\n" +
            "DELETE FROM RecipeComment WHERE comment_recipe_post_id = :postId;\n" +
            "\n" +
            "DELETE FROM RecipePostLikes WHERE post_id = :postId;\n" +
            "\n" +
            "DELETE FROM RecipePost WHERE id = :postId;\n" +
            "\n" +
            "COMMIT;";
    public static final String DELETE_I_MADE_POST = "BEGIN;\n" +
            "\n" +
            "DELETE FROM IMadeCommentLikes WHERE comment_id IN (SELECT id FROM IMadeComment WHERE comment_i_made_post_id = :postId);\n" +
            "\n" +
            "DELETE FROM IMadeComment WHERE comment_i_made_post_id = :postId;\n" +
            "\n" +
            "DELETE FROM IMadePostLikes WHERE post_id = :postId;\n" +
            "\n" +
            "DELETE FROM IMadePost WHERE id = :postId;\n" +
            "\n" +
            "COMMIT;";
    public static final String DELETE_OTHER_POST = "BEGIN;\n" +
            "\n" +
            "DELETE FROM OtherCommentLikes WHERE comment_id IN (SELECT id FROM OtherComment WHERE comment_other_post_id = :postId);\n" +
            "\n" +
            "DELETE FROM OtherComment WHERE comment_other_post_id = :postId;\n" +
            "\n" +
            "DELETE FROM OtherPostLikes WHERE post_id = :postId;\n" +
            "\n" +
            "DELETE FROM OtherPost WHERE id = :postId;\n" +
            "\n" +
            "COMMIT;";

    public static final String INSERT_GARDENING_COMMENT_QUERY = "INSERT INTO GardeningComment(date, comment_text, likes, parent_comment_id, comment_user_id, comment_gardening_post_id)" +
            "VALUES (:date, :comment_text, :likes, :parent_comment_id, :comment_user_id, :comment_gardening_post_id)";

    public static final String INSERT_RECIPE_COMMENT_QUERY = "INSERT INTO RecipeComment(date, comment_text, likes, parent_comment_id, comment_user_id, comment_recipe_post_id)" +
            "VALUES (:date, :comment_text, :likes, :parent_comment_id, :comment_user_id, :comment_recipe_post_id)";

    public static final String INSERT_I_MADE_COMMENT_QUERY = "INSERT INTO IMadeComment(date, comment_text, likes, parent_comment_id, comment_user_id, comment_i_made_post_id)" +
            "VALUES (:date, :comment_text, :likes, :parent_comment_id, :comment_user_id, :comment_i_made_post_id)";

    public static final String INSERT_OTHER_COMMENT_QUERY = "INSERT INTO OtherComment(date, comment_text, likes, parent_comment_id, comment_user_id, comment_other_post_id)" +
            "VALUES (:date, :comment_text, :likes, :parent_comment_id, :comment_user_id, :comment_other_post_id)";

    public static final String SELECT_GARDENING_POST_BY_ID = "SELECT * FROM GardeningPost WHERE id = :id";
    public static final String SELECT_RECIPE_POST_BY_ID = "SELECT * FROM RecipePost WHERE id = :id";
    public static final String SELECT_I_MADE_POST_BY_ID = "SELECT * FROM IMadePost WHERE id = :id";
    public static final String SELECT_OTHER_POST_BY_ID = "SELECT * FROM OtherPost WHERE id = :id";

    public static final String SELECT_ALL_GARDENING_COMMENTS_BY_POST_ID = "SELECT GardeningComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM GardeningComment\n" +
            "INNER JOIN GardeningPost ON GardeningComment.comment_gardening_post_id = GardeningPost.id\n" +
            "INNER JOIN Users ON GardeningComment.comment_user_id = Users.id\n" +
            "WHERE GardeningPost.id = :comment_gardening_post_id\n" +
            "ORDER BY GardeningPost.date DESC, GardeningComment.date DESC";

    public static final String SELECT_ALL_RECIPE_COMMENTS_BY_POST_ID = "SELECT RecipeComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM RecipeComment\n" +
            "INNER JOIN RecipePost ON RecipeComment.comment_recipe_post_id = RecipePost.id\n" +
            "INNER JOIN Users ON RecipeComment.comment_user_id = Users.id\n" +
            "WHERE RecipePost.id = :comment_recipe_post_id\n" +
            "ORDER BY RecipePost.date DESC, RecipeComment.date DESC";

    public static final String SELECT_ALL_I_MADE_COMMENTS_BY_POST_ID = "SELECT IMadeComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM IMadeComment\n" +
            "INNER JOIN IMadePost ON IMadeComment.comment_i_made_post_id = IMadePost.id\n" +
            "INNER JOIN Users ON IMadeComment.comment_user_id = Users.id\n" +
            "WHERE IMadePost.id = :comment_i_made_post_id\n" +
            "ORDER BY IMadePost.date DESC, IMadeComment.date DESC";

    public static final String SELECT_ALL_OTHER_COMMENTS_BY_POST_ID = "SELECT OtherComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM OtherComment\n" +
            "INNER JOIN OtherPost ON OtherComment.comment_other_post_id = OtherPost.id\n" +
            "INNER JOIN Users ON OtherComment.comment_user_id = Users.id\n" +
            "WHERE OtherPost.id = :comment_other_post_id\n" +
            "ORDER BY OtherPost.date DESC, OtherComment.date DESC";

    public static final String UPDATE_GARDENING_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE GardeningComment SET comment_text = :comment_text WHERE id = :id";

    public static final String UPDATE_RECIPE_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE RecipeComment SET comment_text = :comment_text WHERE id = :id";
    public static final String UPDATE_I_MADE_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE IMadeComment SET comment_text = :comment_text WHERE id = :id";
    public static final String UPDATE_OTHER_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE OtherComment SET comment_text = :comment_text WHERE id = :id";

    public static final String DELETE_GARDENING_COMMENT = "DELETE FROM GardeningComment WHERE id = :id";
    public static final String DELETE_RECIPE_COMMENT = "DELETE FROM RecipeComment WHERE id = :id";
    public static final String DELETE_I_MADE_COMMENT = "DELETE FROM IMadeComment WHERE id = :id";
    public static final String DELETE_OTHER_COMMENT = "DELETE FROM OtherComment WHERE id = :id";

    public static  final String UPDATE_PLUS_GARDENING_LIKES = "UPDATE GardeningPost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_RECIPE_LIKES = "UPDATE RecipePost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_I_MADE_LIKES = "UPDATE IMadePost SET likes = likes + 1 WHERE id = :id";
    public static  final String UPDATE_PLUS_OTHER_LIKES = "UPDATE OtherPost SET likes = likes + 1 WHERE id = :id";

    public static final String UPDATE_PLUS_GARDENING_COMMENT_LIKES = "UPDATE GardeningComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_RECIPE_COMMENT_LIKES = "UPDATE RecipeComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_I_MADE_COMMENT_LIKES = "UPDATE IMadeComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_OTHER_COMMENT_LIKES = "UPDATE OtherComment SET likes = likes + 1 WHERE id = :id";

    public static  final String UPDATE_MINUS_GARDENING_LIKES = "UPDATE GardeningPost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_RECIPE_LIKES = "UPDATE RecipePost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_I_MADE_LIKES = "UPDATE IMadePost SET likes = likes - 1 WHERE id = :id";
    public static  final String UPDATE_MINUS_OTHER_LIKES = "UPDATE OtherPost SET likes = likes - 1 WHERE id = :id";

    public static final String UPDATE_MINUS_GARDENING_COMMENT_LIKES = "UPDATE GardeningComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_RECIPE_COMMENT_LIKES = "UPDATE RecipeComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_I_MADE_COMMENT_LIKES = "UPDATE IMadeComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_OTHER_COMMENT_LIKES = "UPDATE OtherComment SET likes = likes - 1 WHERE id = :id";
    public static final String ADD_GARDENING_POST_LIKES_KEY_TABLE = "INSERT INTO GardeningPostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_RECIPE_POST_LIKES_KEY_TABLE = "INSERT INTO RecipePostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_I_MADE_POST_LIKES_KEY_TABLE = "INSERT INTO IMadePostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";
    public static final String ADD_OTHER_POST_LIKES_KEY_TABLE = "INSERT INTO OtherPostLikes (user_id, post_id)\n" +
            "VALUES (:userId, :postId)";

    public static final String ADD_GARDENING_COMMENT_LIKES_KEY_TABLE = "INSERT INTO GardeningCommentLikes (user_id, comment_id)\n" +
        "VALUES (:userId, :commentId)";
    public static final String ADD_RECIPE_COMMENT_LIKES_KEY_TABLE = "INSERT INTO RecipeCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";
    public static final String ADD_I_MADE_COMMENT_LIKES_KEY_TABLE = "INSERT INTO IMadeCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";
    public static final String ADD_OTHER_COMMENT_LIKES_KEY_TABLE = "INSERT INTO OtherCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";

    public static final String DELETE_GARDENING_POST_LIKES_KEY_TABLE = "DELETE FROM GardeningPostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";

    public static final String DELETE_RECIPE_POST_LIKES_KEY_TABLE = "DELETE FROM RecipePostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String DELETE_I_MADE_POST_LIKES_KEY_TABLE = "DELETE FROM IMadePostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";
    public static final String DELETE_OTHER_POST_LIKES_KEY_TABLE = "DELETE FROM OtherPostLikes\n" +
            "WHERE user_id = :userId AND post_id = :postId";

    public static final String DELETE_GARDENING_COMMENT_LIKES_KEY_TABLE = "DELETE FROM GardeningCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_RECIPE_COMMENT_LIKES_KEY_TABLE = "DELETE FROM RecipeCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_I_MADE_COMMENT_LIKES_KEY_TABLE = "DELETE FROM IMadeCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_OTHER_COMMENT_LIKES_KEY_TABLE = "DELETE FROM OtherCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";

    public static final String GET_ALL_GARDENING_POST_LIKES = "SELECT likes FROM GardeningPost WHERE id = :postId";
    public static final String GET_ALL_RECIPE_POST_LIKES = "SELECT likes FROM RecipePost WHERE id = :postId";
    public static final String GET_ALL_I_MADE_POST_LIKES = "SELECT likes FROM IMadePost WHERE id = :postId";
    public static final String GET_ALL_OTHER_POST_LIKES = "SELECT likes FROM OtherPost WHERE id = :postId";

    public static final String GET_ALL_GARDENING_COMMENT_LIKES = "SELECT likes FROM GardeningComment WHERE id = :commentId";
    public static final String GET_ALL_RECIPE_COMMENT_LIKES = "SELECT likes FROM RecipeComment WHERE id = :commentId";
    public static final String GET_ALL_I_MADE_COMMENT_LIKES = "SELECT likes FROM IMadeComment WHERE id = :commentId";
    public static final String GET_ALL_OTHER_COMMENT_LIKES = "SELECT likes FROM OtherComment WHERE id = :commentId";

    public static final String SELECT_GARDENING_COMMENT_BY_ID_QUERY = "SELECT * FROM GardeningComment WHERE id = :id";

    public static final String SELECT_ALL_POSTS_LIKED_BY_USER = "SELECT post_id FROM GardeningPostLikes WHERE user_id = :userId;";
     public static final String SELECT_LATEST_COMMENT_FROM_GARDENING_POST = "SELECT * FROM GardeningComment WHERE comment_gardening_post_id = :postId ORDER BY date DESC LIMIT 1";
}
