package com.example.demo.rowmapper;

import com.example.demo.domain.GardeningPost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GardeningPostRowMapper implements RowMapper<GardeningPost> {
    @Override
    public GardeningPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        GardeningPost gardeningPost = new GardeningPost();
        gardeningPost.setId(rs.getLong(1));
        gardeningPost.setDate(rs.getTimestamp(2).toLocalDateTime());
        gardeningPost.setTitle(rs.getString(3));
        gardeningPost.setDescription(rs.getString(4));
        gardeningPost.setTag(rs.getString(5));
        gardeningPost.setLikes((long) rs.getInt(6));
        gardeningPost.setView_count((long) rs.getInt(7));
        gardeningPost.setImg_url(rs.getString(8));
        gardeningPost.setUsers_gardening_post_id(rs.getLong(9));
        gardeningPost.setAuthor_user_id(rs.getLong("author_user_id"));
        gardeningPost.setUsername(rs.getString("username"));
        gardeningPost.setUser_image_url(rs.getString("user_image_url"));
        return gardeningPost;
    }
}
