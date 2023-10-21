package com.example.demo.rowmapper;

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
        gardeningPost.setLikes((long) rs.getInt(1));
        return gardeningPost;
    }
}
