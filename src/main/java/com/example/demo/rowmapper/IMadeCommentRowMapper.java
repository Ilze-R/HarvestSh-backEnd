package com.example.demo.rowmapper;

import com.example.demo.domain.IMadeComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IMadeCommentRowMapper implements RowMapper<IMadeComment> {
    @Override
    public IMadeComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        IMadeComment imadecomment = new IMadeComment();
        imadecomment.setId(rs.getLong(1));
        imadecomment.setDate(rs.getTimestamp(2).toLocalDateTime());
        imadecomment.setComment_text(rs.getString(3));
        imadecomment.setLikes((long) rs.getInt(4));
        imadecomment.setParent_comment_id((long) rs.getInt(5));
        imadecomment.setComment_user_id((long) rs.getInt(6));
       imadecomment.setUser_image_url(rs.getString("user_image_url"));
        imadecomment.setUsername(rs.getString("username"));
        return imadecomment;
    }
}