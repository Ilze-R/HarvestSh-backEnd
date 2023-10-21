package com.example.demo.rowmapper;

import com.example.demo.domain.LikedOtherComment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikedOtherCommRowMapper implements RowMapper<LikedOtherComment> {
    @Override
    public LikedOtherComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LikedOtherComment likedOtherComment = new LikedOtherComment();
        likedOtherComment.setLikes((long) rs.getInt(1));
        return likedOtherComment;
    }
}
