package com.example.demo.query;

public class PostQuery {
    public static final String INSERT_GARDENING_POST_QUERY = "INSERT INTO GardeningPost(date, title, description, tag, likes, view_count," +
            " img_url, users_gardening_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_gardening_post_id)";

    public static final String INSERT_RECIPE_POST_QUERY = "INSERT INTO RecipePost(date, title, description, tag, likes, view_count, img_url, users_recipes_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_recipes_post_id)";

    public static final String INSERT_I_MADE_POST_QUERY = "INSERT INTO IMadePost(date, title, description, tag, likes, view_count, img_url, users_i_made_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_i_made_post_id)";

    public static final String INSERT_OTHER_POST_QUERY = "INSERT INTO OtherPost(date, title, description, tag, likes, view_count, img_url, users_other_post_id)"+
            "VALUES (:date, :title, :description, :tag, :likes, :view_count, :img_url, :users_other_post_id)";

    public static final String INSERT_GARDENING_COMMENT_QUERY = "INSERT INTO GardeningComment(date, comment_text, parent_comment_id, comment_user_id, comment_gardening_post_id)" +
            "VALUES (:date, :comment_text, :parent_comment_id, :comment_user_id, :comment_gardening_post_id)";

    public static final String SELECT_GARDENING_POST_BY_ID = "SELECT * FROM GardeningPost WHERE id = :id";
}
