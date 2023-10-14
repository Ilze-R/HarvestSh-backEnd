package com.example.demo.rowmapper;

import com.example.demo.domain.LikedOtherPost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedOtherPostRowMapper implements RowMapper<LikedOtherPost> {
    @Override
    public LikedOtherPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedOtherPost likedOtherPost = new LikedOtherPost();
        likedOtherPost.setId(rs.getLong(1));
        likedOtherPost.setDate(rs.getTimestamp(2).toLocalDateTime());
        likedOtherPost.setTitle(rs.getString(3));
        likedOtherPost.setDescription(rs.getString(4));
        likedOtherPost.setTag(rs.getString(5));
        likedOtherPost.setLikes((long) rs.getInt(6));
        likedOtherPost.setView_count((long) rs.getInt(7));
        likedOtherPost.setImg_url(rs.getString(8));
        return likedOtherPost;
    }
}