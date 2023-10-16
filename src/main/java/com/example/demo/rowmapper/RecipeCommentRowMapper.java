package com.example.demo.rowmapper;

import com.example.demo.domain.RecipeComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RecipeCommentRowMapper implements RowMapper<RecipeComment> {
    @Override
    public RecipeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecipeComment recipeComment = new RecipeComment();
        recipeComment.setId(rs.getLong(1));
        recipeComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        recipeComment.setComment_text(rs.getString(3));
        recipeComment.setLikes((long) rs.getInt(4));
        recipeComment.setParent_comment_id((long) rs.getInt(5));
        recipeComment.setComment_user_id((long) rs.getInt(6));
        recipeComment.setUser_image_url(rs.getString("user_image_url"));
        recipeComment.setUsername(rs.getString("username"));
        return recipeComment;
    }
}