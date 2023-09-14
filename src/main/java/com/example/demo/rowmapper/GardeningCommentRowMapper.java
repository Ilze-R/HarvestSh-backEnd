package com.example.demo.rowmapper;

import com.example.demo.domain.GardeningComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GardeningCommentRowMapper implements RowMapper<GardeningComment> {
    @Override
    public GardeningComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        GardeningComment gardeningComment = new GardeningComment();
        gardeningComment.setId(rs.getLong(1));
        gardeningComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        gardeningComment.setComment_text(rs.getString(3));
        gardeningComment.setParent_comment_id((long) rs.getInt(4));
        gardeningComment.setComment_user_id((long) rs.getInt(5));
        gardeningComment.setUser_image_url(rs.getString("user_image_url"));
        gardeningComment.setUsername(rs.getString("username"));
        return gardeningComment;
    }
}
