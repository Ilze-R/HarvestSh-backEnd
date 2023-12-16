package com.example.demo.query;

public class CommentQuery {

    public static final String INSERT_GARDENING_COMMENT_QUERY = "INSERT INTO GardeningComment(date, comment_text, reply_username, likes, parent_comment_id, comment_user_id, comment_gardening_post_id)" +
            "VALUES (:date, :comment_text, :reply_username, :likes, :parent_comment_id, :comment_user_id, :comment_gardening_post_id)";

    public static final String INSERT_RECIPE_COMMENT_QUERY = "INSERT INTO RecipeComment(date, comment_text, reply_username, likes, parent_comment_id, comment_user_id, comment_recipe_post_id)" +
            "VALUES (:date, :comment_text, :reply_username, :likes, :parent_comment_id, :comment_user_id, :comment_recipe_post_id)";

    public static final String INSERT_I_MADE_COMMENT_QUERY = "INSERT INTO IMadeComment(date, comment_text, reply_username, likes, parent_comment_id, comment_user_id, comment_i_made_post_id)" +
            "VALUES (:date, :comment_text, :reply_username, :likes, :parent_comment_id, :comment_user_id, :comment_i_made_post_id)";

    public static final String INSERT_OTHER_COMMENT_QUERY = "INSERT INTO OtherComment(date, comment_text, reply_username, likes, parent_comment_id, comment_user_id, comment_other_post_id)" +
            "VALUES (:date, :comment_text, :reply_username, :likes, :parent_comment_id, :comment_user_id, :comment_other_post_id)";

    public static final String SELECT_ALL_GARDENING_COMMENTS_BY_POST_ID = """
            SELECT GardeningComment.*, Users.image_url AS user_image_url, Users.username AS username
            FROM GardeningComment
            INNER JOIN GardeningPost ON GardeningComment.comment_gardening_post_id = GardeningPost.id
            INNER JOIN Users ON GardeningComment.comment_user_id = Users.id
            WHERE GardeningPost.id = :comment_gardening_post_id
            ORDER BY GardeningPost.date DESC, GardeningComment.date ASC""";

    public static final String SELECT_ALL_RECIPE_COMMENTS_BY_POST_ID = """
            SELECT RecipeComment.*, Users.image_url AS user_image_url, Users.username AS username
            FROM RecipeComment
            INNER JOIN RecipePost ON RecipeComment.comment_recipe_post_id = RecipePost.id
            INNER JOIN Users ON RecipeComment.comment_user_id = Users.id
            WHERE RecipePost.id = :comment_recipe_post_id
            ORDER BY RecipePost.date DESC, RecipeComment.date ASC""";

    public static final String SELECT_ALL_I_MADE_COMMENTS_BY_POST_ID = """
            SELECT IMadeComment.*, Users.image_url AS user_image_url, Users.username AS username
            FROM IMadeComment
            INNER JOIN IMadePost ON IMadeComment.comment_i_made_post_id = IMadePost.id
            INNER JOIN Users ON IMadeComment.comment_user_id = Users.id
            WHERE IMadePost.id = :comment_i_made_post_id
            ORDER BY IMadePost.date DESC, IMadeComment.date ASC""";

    public static final String SELECT_ALL_OTHER_COMMENTS_BY_POST_ID = """
            SELECT OtherComment.*, Users.image_url AS user_image_url, Users.username AS username
            FROM OtherComment
            INNER JOIN OtherPost ON OtherComment.comment_other_post_id = OtherPost.id
            INNER JOIN Users ON OtherComment.comment_user_id = Users.id
            WHERE OtherPost.id = :comment_other_post_id
            ORDER BY OtherPost.date DESC, OtherComment.date ASC""";

    public static final String SELECT_GARDENING_COMMENT_POST_ID = "SELECT comment_gardening_post_id FROM GardeningComment WHERE id = :id";
    public static final String SELECT_RECIPE_COMMENT_POST_ID = "SELECT comment_recipe_post_id FROM RecipeComment WHERE id = :id";
    public static final String SELECT_I_MADE_COMMENT_POST_ID = "SELECT comment_i_made_post_id FROM IMadeComment WHERE id = :id";
    public static final String SELECT_OTHER_COMMENT_POST_ID = "SELECT comment_other_post_id FROM OtherComment WHERE id = :id";
    public static final String UPDATE_GARDENING_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE GardeningComment SET comment_text = :comment_text WHERE id = :id";
    public static final String UPDATE_RECIPE_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE RecipeComment SET comment_text = :comment_text WHERE id = :id";
    public static final String UPDATE_I_MADE_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE IMadeComment SET comment_text = :comment_text WHERE id = :id";
    public static final String UPDATE_OTHER_COMMENT_BY_COMMENT_ID_QUERY = "UPDATE OtherComment SET comment_text = :comment_text WHERE id = :id";

    public static final String DELETE_GARDENING_COMMENT = "DELETE FROM GardeningComment WHERE id = :id";
    public static final String DELETE_RECIPE_COMMENT = "DELETE FROM RecipeComment WHERE id = :id";
    public static final String DELETE_I_MADE_COMMENT = "DELETE FROM IMadeComment WHERE id = :id";
    public static final String DELETE_OTHER_COMMENT = "DELETE FROM OtherComment WHERE id = :id";

    public static final String UPDATE_PLUS_GARDENING_COMMENT_LIKES = "UPDATE GardeningComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_RECIPE_COMMENT_LIKES = "UPDATE RecipeComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_I_MADE_COMMENT_LIKES = "UPDATE IMadeComment SET likes = likes + 1 WHERE id = :id";
    public static final String UPDATE_PLUS_OTHER_COMMENT_LIKES = "UPDATE OtherComment SET likes = likes + 1 WHERE id = :id";
    public static final String ADD_COMMENT_NOTIFICATION = "INSERT INTO Notifications (user_id, action_user_id, message, is_read, created_at, forum_type, target, target_id, post_id) VALUES (:user_id, :action_user_id, :message, :is_read, :created_at, :forum_type, :target, :target_id, :post_id)";
    public static final String DELETE_COMMENT_NOTIFICATION = "DELETE FROM Notifications WHERE forum_type = :forum_type AND target = :target AND target_id = :target_id AND post_id = :post_id";
    public static final String UPDATE_MINUS_GARDENING_COMMENT_LIKES = "UPDATE GardeningComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_RECIPE_COMMENT_LIKES = "UPDATE RecipeComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_I_MADE_COMMENT_LIKES = "UPDATE IMadeComment SET likes = likes - 1 WHERE id = :id";
    public static final String UPDATE_MINUS_OTHER_COMMENT_LIKES = "UPDATE OtherComment SET likes = likes - 1 WHERE id = :id";

    public static final String ADD_GARDENING_COMMENT_LIKES_KEY_TABLE = "INSERT INTO GardeningCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";
    public static final String ADD_RECIPE_COMMENT_LIKES_KEY_TABLE = "INSERT INTO RecipeCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";
    public static final String ADD_I_MADE_COMMENT_LIKES_KEY_TABLE = "INSERT INTO IMadeCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";
    public static final String ADD_OTHER_COMMENT_LIKES_KEY_TABLE = "INSERT INTO OtherCommentLikes (user_id, comment_id)\n" +
            "VALUES (:userId, :commentId)";

    public static final String DELETE_GARDENING_COMMENT_LIKES_KEY_TABLE = "DELETE FROM GardeningCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_RECIPE_COMMENT_LIKES_KEY_TABLE = "DELETE FROM RecipeCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_I_MADE_COMMENT_LIKES_KEY_TABLE = "DELETE FROM IMadeCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";
    public static final String DELETE_OTHER_COMMENT_LIKES_KEY_TABLE = "DELETE FROM OtherCommentLikes\n" +
            "WHERE user_id = :userId AND comment_id = :commentId";

    public static final String GET_ALL_GARDENING_COMMENT_LIKES = "SELECT likes FROM GardeningComment WHERE id = :commentId";
    public static final String GET_ALL_RECIPE_COMMENT_LIKES = "SELECT likes FROM RecipeComment WHERE id = :commentId";
    public static final String GET_ALL_I_MADE_COMMENT_LIKES = "SELECT likes FROM IMadeComment WHERE id = :commentId";
    public static final String GET_ALL_OTHER_COMMENT_LIKES = "SELECT likes FROM OtherComment WHERE id = :commentId";

    public static final String SELECT_GARDENING_COMMENT_BY_ID_QUERY = "SELECT * FROM GardeningComment WHERE id = :id";

    public static final String SELECT_LATEST_COMMENT_FROM_GARDENING_POST = "SELECT * FROM GardeningComment WHERE comment_gardening_post_id = :postId ORDER BY date DESC LIMIT 1";

}
