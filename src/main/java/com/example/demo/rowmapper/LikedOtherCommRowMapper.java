package com.example.demo.rowmapper;

import com.example.demo.domain.LikedOtherComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedOtherCommRowMapper implements RowMapper<LikedOtherComment> {
    @Override
    public LikedOtherComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedOtherComment likedOtherComment = new LikedOtherComment();
        likedOtherComment.setId(rs.getLong(1));
        likedOtherComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        likedOtherComment.setComment_text(rs.getString(3));
        likedOtherComment.setLikes((long) rs.getInt(4));
        likedOtherComment.setParent_comment_id((long) rs.getInt(5));
        likedOtherComment.setComment_user_id((long) rs.getInt(6));
        return likedOtherComment;
    }
}
