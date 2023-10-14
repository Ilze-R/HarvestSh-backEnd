package com.example.demo.rowmapper;

import com.example.demo.domain.LikedRecipePost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedRecipePostRowMapper implements RowMapper<LikedRecipePost> {
    @Override
    public LikedRecipePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedRecipePost recipePost = new LikedRecipePost();
        recipePost.setId(rs.getLong(1));
        recipePost.setDate(rs.getTimestamp(2).toLocalDateTime());
        recipePost.setTitle(rs.getString(3));
        recipePost.setDescription(rs.getString(4));
        recipePost.setTag(rs.getString(5));
        recipePost.setLikes((long) rs.getInt(6));
        recipePost.setView_count((long) rs.getInt(7));
        recipePost.setImg_url(rs.getString(8));
        return recipePost;
    }
}