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
        likedOtherPost.setLikes((long) rs.getInt(2));
        return likedOtherPost;
    }
}