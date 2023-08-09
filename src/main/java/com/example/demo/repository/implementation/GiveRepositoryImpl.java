package com.example.demo.repository.implementation;

import com.example.demo.domain.Give;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.GiveRepository;
import com.example.demo.rowmapper.GiveRowMapper;
import com.example.demo.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.example.demo.query.GiveQuery.*;
import static java.util.Map.of;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GiveRepositoryImpl implements GiveRepository<Give> {

    private final NamedParameterJdbcTemplate jdbc;
    private final GiveRowMapper giveRowMapper;
    @Override
    public Give get(Long id) {
        try {
            return jdbc.queryForObject(SELECT_GIVE_BY_ID_QUERY, of("id", id), new GiveRowMapper());
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("No give found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public Give create(Give give, Long userId) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("type", give.getType());
            parameters.addValue("amount", give.getAmount());
            parameters.addValue("status", give.getStatus());
            parameters.addValue("users_give_id", userId);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_GIVE_QUERY, parameters, keyHolder);

            long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
            give.setId(generatedId);
            give.setDate(currentDateTime);
            return give;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

//    @Override
//    public Collection<Give> list() {
//        log.info("Fetching all Gives");
//        try {
//            return jdbc.query(SELECT_GIVES_QUERY, new GiveRowMapper());
//        } catch (Exception exception){
//            throw new ApiException("An error occurred. Please try again");
//        }
//    }
@Override
public Collection<Give> listForUser(Long userId) {
    log.info("Fetching gives for user with ID: {}", userId);
    try {
        SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);

        return jdbc.query(SELECT_GIVES_FOR_USER_QUERY, namedParameters, new GiveRowMapper());
    } catch (Exception exception) {
        log.error("Error fetching Gives: {}", exception.getMessage());
        throw new ApiException("An error occurred while fetching Gives");
    }
}

//    @Override
//    public List<Give> findAll() {
//        String sql = "SELECT id, date, type, amount, status, users_id FROM Give";
//        return jdbc.query(sql, giveRowMapper);
//    }

}
