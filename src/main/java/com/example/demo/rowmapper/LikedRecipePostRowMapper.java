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
        recipePost.setLikes((long) rs.getInt(2));
        return recipePost;
    }
}