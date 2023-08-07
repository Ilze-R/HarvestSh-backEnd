package com.example.demo.repository.implementation;

import com.example.demo.domain.Give;
import com.example.demo.domain.Users;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.GiveRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.rowmapper.GiveRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.example.demo.query.GiveQuery.INSERT_GIVE_QUERY;
import static com.example.demo.query.GiveQuery.SELECT_GIVE_BY_ID_QUERY;
import static java.util.Map.of;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GiveRepositoryImpl implements GiveRepository<Give> {

    private final NamedParameterJdbcTemplate jdbc;
    private final GiveRowMapper giveRowMapper;
    private final UserRepository<Users> userRepository;
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
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(give.getDate()));
            parameters.addValue("type", give.getType());
            parameters.addValue("amount", give.getAmount());
            parameters.addValue("status", give.getStatus());
            parameters.addValue("users_give_id", userId);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_GIVE_QUERY, parameters, keyHolder);

            long generatedId = keyHolder.getKey().longValue();
            give.setId(generatedId);
            return give;
        } catch (Exception exception){
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public List<Give> findAll() {
        String sql = "SELECT id, date, type, amount, status, users_id FROM Give";
        return jdbc.query(sql, giveRowMapper);
    }

//    private SqlParameterSource getSqlParameterSource(Give give) {
//        return new MapSqlParameterSource()
//                .addValue("type", give.getType())
//                .addValue("amount", give.getAmount())
//                .addValue("status",give.getStatus());
////                .addValue("users_id", give.getUsers_id());
//    }
    
}
