package com.example.demo.rowmapper;

import com.example.demo.domain.OtherComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OtherCommentRowMapper implements RowMapper<OtherComment> {
    @Override
    public OtherComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        OtherComment otherComment = new OtherComment();
        otherComment.setId(rs.getLong(1));
        otherComment.setDate(rs.getTimestamp(2).toLocalDateTime());
        otherComment.setComment_text(rs.getString(3));
        otherComment.setReply_username(rs.getString(4));
        otherComment.setLikes((long) rs.getInt(5));
        otherComment.setParent_comment_id((long) rs.getInt(6));
        otherComment.setComment_user_id((long) rs.getInt(7));
        otherComment.setUser_image_url(rs.getString("user_image_url"));
        otherComment.setUsername(rs.getString("username"));
        return otherComment;
    }
}
