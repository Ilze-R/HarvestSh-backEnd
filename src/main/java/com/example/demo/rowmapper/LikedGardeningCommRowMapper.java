package com.example.demo.rowmapper;

import com.example.demo.domain.LikedGardeningComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedGardeningCommRowMapper implements RowMapper<LikedGardeningComment> {
    @Override
    public LikedGardeningComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedGardeningComment likedGardeningComment = new LikedGardeningComment();
        likedGardeningComment.setId(rs.getLong(1));
        likedGardeningComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        likedGardeningComment.setComment_text(rs.getString(3));
        likedGardeningComment.setLikes((long) rs.getInt(4));
        likedGardeningComment.setParent_comment_id((long) rs.getInt(5));
        likedGardeningComment.setComment_user_id((long) rs.getInt(6));
        return likedGardeningComment;
    }
}
