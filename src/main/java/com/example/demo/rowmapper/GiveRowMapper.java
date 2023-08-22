package com.example.demo.rowmapper;

import com.example.demo.domain.Give;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiveRowMapper implements RowMapper<Give> {
    @Override
    public Give mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Give.builder()
                .id(resultSet.getLong("id"))
                .date(resultSet.getTimestamp("date").toLocalDateTime())
                .type(resultSet.getString("type"))
                .amount(resultSet.getDouble("amount"))
                .amountType(resultSet.getString("amount_type"))
                .description(resultSet.getString("description"))
                .img_url(resultSet.getString("img_url"))
                .location(resultSet.getString("location"))
                .status(resultSet.getString("status"))
                .build();
    }
}
