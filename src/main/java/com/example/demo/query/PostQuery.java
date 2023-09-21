package com.example.demo.query;

import com.example.demo.domain.Users;

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

    public static final String INSERT_GARDENING_COMMENT_QUERY = "INSERT INTO GardeningComment(date, comment_text, parent_comment_id, comment_user_id, comment_gardening_post_id)" +
            "VALUES (:date, :comment_text, :parent_comment_id, :comment_user_id, :comment_gardening_post_id)";

    public static final String INSERT_RECIPE_COMMENT_QUERY = "INSERT INTO RecipeComment(date, comment_text, parent_comment_id, comment_user_id, comment_recipe_post_id)" +
            "VALUES (:date, :comment_text, :parent_comment_id, :comment_user_id, :comment_recipe_post_id)";

    public static final String INSERT_I_MADE_COMMENT_QUERY = "INSERT INTO IMadeComment(date, comment_text, parent_comment_id, comment_user_id, comment_i_made_post_id)" +
            "VALUES (:date, :comment_text, :parent_comment_id, :comment_user_id, :comment_i_made_post_id)";

    public static final String INSERT_OTHER_COMMENT_QUERY = "INSERT INTO OtherComment(date, comment_text, parent_comment_id, comment_user_id, comment_other_post_id)" +
            "VALUES (:date, :comment_text, :parent_comment_id, :comment_user_id, :comment_other_post_id)";

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

    public static final String SELECT_GARDENING_COMMENT_BY_ID_QUERY = "SELECT * FROM GardeningComment WHERE id = :id";
}
