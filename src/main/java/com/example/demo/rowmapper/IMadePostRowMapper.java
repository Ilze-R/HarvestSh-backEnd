package com.example.demo.rowmapper;

import com.example.demo.domain.IMadePost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IMadePostRowMapper implements RowMapper<IMadePost> {
    @Override
    public IMadePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        IMadePost iMadePost = new IMadePost();
        iMadePost.setDate(rs.getTimestamp(2).toLocalDateTime());
        iMadePost.setTitle(rs.getString(3));
        iMadePost.setDescription(rs.getString(4));
        iMadePost.setTag(rs.getString(5));
        iMadePost.setLikes((long) rs.getInt(6));
        iMadePost.setView_count((long) rs.getInt(7));
        iMadePost.setImg_url(rs.getString(8));
        return iMadePost;
    }
}