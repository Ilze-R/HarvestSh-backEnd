package com.example.demo.rowmapper;

import com.example.demo.domain.OtherPost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OtherPostRowMapper implements RowMapper<OtherPost> {
    @Override
    public OtherPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        OtherPost otherPost = new OtherPost();
        otherPost.setId(rs.getLong(1));
        otherPost.setDate(rs.getTimestamp(2).toLocalDateTime());
        otherPost.setTitle(rs.getString(3));
        otherPost.setDescription(rs.getString(4));
        otherPost.setTag(rs.getString(5));
        otherPost.setLikes((long) rs.getInt(6));
        otherPost.setView_count((long) rs.getInt(7));
        otherPost.setImg_url(rs.getString(8));
        otherPost.setAuthor_user_id(rs.getLong("author_user_id"));
        otherPost.setUsername(rs.getString("username"));
        otherPost.setUser_image_url(rs.getString("user_image_url"));
        return otherPost;
    }
}