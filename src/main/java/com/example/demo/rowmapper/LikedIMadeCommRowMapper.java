package com.example.demo.rowmapper;

import com.example.demo.domain.LikedIMadeComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedIMadeCommRowMapper implements RowMapper<LikedIMadeComment> {
    @Override
    public LikedIMadeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedIMadeComment likedIMadeComment = new LikedIMadeComment();
        likedIMadeComment.setId(rs.getLong(1));
        likedIMadeComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        likedIMadeComment.setComment_text(rs.getString(3));
        likedIMadeComment.setLikes((long) rs.getInt(4));
        likedIMadeComment.setParent_comment_id((long) rs.getInt(5));
        likedIMadeComment.setComment_user_id((long) rs.getInt(6));
        return likedIMadeComment;
    }
}
