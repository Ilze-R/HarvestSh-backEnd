package com.example.demo.rowmapper;

import com.example.demo.domain.RecipePost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecipePostRowMapper implements RowMapper<RecipePost> {
    @Override
    public RecipePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecipePost recipePost = new RecipePost();
        recipePost.setId(rs.getLong(1));
        recipePost.setDate(rs.getTimestamp(2).toLocalDateTime());
        recipePost.setTitle(rs.getString(3));
        recipePost.setDescription(rs.getString(4));
        recipePost.setTag(rs.getString(5));
        recipePost.setLikes((long) rs.getInt(6));
        recipePost.setView_count((long) rs.getInt(7));
        recipePost.setImg_url(rs.getString(8));
        recipePost.setUser_image_url(rs.getString("user_image_url"));
        return recipePost;
    }
}