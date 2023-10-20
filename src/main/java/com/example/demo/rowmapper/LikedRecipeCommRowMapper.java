package com.example.demo.rowmapper;

import com.example.demo.domain.LikedRecipeComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedRecipeCommRowMapper implements RowMapper<LikedRecipeComment> {
    @Override
    public LikedRecipeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedRecipeComment likedRecipeComment = new LikedRecipeComment();
        likedRecipeComment.setId(rs.getLong(1));
        likedRecipeComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        likedRecipeComment.setComment_text(rs.getString(3));
        likedRecipeComment.setLikes((long) rs.getInt(4));
        likedRecipeComment.setParent_comment_id((long) rs.getInt(5));
        likedRecipeComment.setComment_user_id((long) rs.getInt(6));
        return likedRecipeComment;
    }
}