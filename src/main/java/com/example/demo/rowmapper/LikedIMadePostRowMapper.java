package com.example.demo.rowmapper;

import com.example.demo.domain.LikedIMadePost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedIMadePostRowMapper implements RowMapper<LikedIMadePost> {
    @Override
    public LikedIMadePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedIMadePost iMadePost = new LikedIMadePost();
        iMadePost.setLikes((long) rs.getInt(1));
        return iMadePost;
    }
}
