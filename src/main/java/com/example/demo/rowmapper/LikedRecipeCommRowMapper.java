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
        likedRecipeComment.setLikes((long) rs.getInt(2));
        return likedRecipeComment;
    }
}