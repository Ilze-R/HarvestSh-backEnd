package com.example.demo.rowmapper;

import com.example.demo.domain.GardeningPost;
import com.example.demo.domain.LikedGardeningPost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedGardeningPostRowMapper implements RowMapper<LikedGardeningPost> {
    @Override
    public LikedGardeningPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedGardeningPost gardeningPost = new LikedGardeningPost();
        gardeningPost.setId(rs.getLong(1));
        gardeningPost.setDate(rs.getTimestamp(2).toLocalDateTime());
        gardeningPost.setTitle(rs.getString(3));
        gardeningPost.setDescription(rs.getString(4));
        gardeningPost.setTag(rs.getString(5));
        gardeningPost.setLikes((long) rs.getInt(6));
        gardeningPost.setView_count((long) rs.getInt(7));
        gardeningPost.setImg_url(rs.getString(8));
        return gardeningPost;
    }
}
