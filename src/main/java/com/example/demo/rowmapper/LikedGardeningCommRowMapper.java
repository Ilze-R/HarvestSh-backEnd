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
        likedGardeningComment.setLikes((long) rs.getInt(2));
        return likedGardeningComment;
    }
}
