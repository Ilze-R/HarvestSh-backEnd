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
            "WHERE GardeningPost.id = :comment_gardening_post_id";

    public static final String SELECT_ALL_RECIPE_COMMENTS_BY_POST_ID = "SELECT RecipeComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM RecipeComment\n" +
            "INNER JOIN RecipePost ON RecipeComment.comment_recipe_post_id = RecipePost.id\n" +
            "INNER JOIN Users ON RecipeComment.comment_user_id = Users.id\n" +
            "WHERE RecipePost.id = :comment_recipe_post_id";

    public static final String SELECT_ALL_I_MADE_COMMENTS_BY_POST_ID = "SELECT IMadeComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM IMadeComment\n" +
            "INNER JOIN IMadePost ON IMadeComment.comment_i_made_post_id = IMadePost.id\n" +
            "INNER JOIN Users ON IMadeComment.comment_user_id = Users.id\n" +
            "WHERE IMadePost.id = :comment_i_made_post_id";

    public static final String SELECT_ALL_OTHER_COMMENTS_BY_POST_ID = "SELECT OtherComment.*, Users.image_url AS user_image_url, Users.username AS username\n" +
            "FROM OtherComment\n" +
            "INNER JOIN OtherPost ON OtherComment.comment_other_post_id = OtherPost.id\n" +
            "INNER JOIN Users ON OtherComment.comment_user_id = Users.id\n" +
            "WHERE OtherPost.id = :comment_other_post_id";

}
