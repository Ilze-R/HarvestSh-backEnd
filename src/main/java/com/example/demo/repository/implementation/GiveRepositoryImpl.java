package com.example.demo.repository.implementation;

import com.example.demo.domain.Give;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.GiveRepository;
import com.example.demo.rowmapper.GiveRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static com.example.demo.query.GiveQuery.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Map.of;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GiveRepositoryImpl implements GiveRepository<Give> {

    private final NamedParameterJdbcTemplate jdbc;
    private final GiveRowMapper giveRowMapper;

    @Override
    public Give get(Long id) {
        try {
            return jdbc.queryForObject(SELECT_GIVE_BY_USER_ID_QUERY, of("id", id), new GiveRowMapper());
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("No give found by id: " + id);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public Give create(Long userId, Give give, MultipartFile image) {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String giveImageUrl = setGiveImageUrl(give.getType());
            give.setImg_url(giveImageUrl);
            saveImage(give.getType(), image);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("date", Timestamp.valueOf(currentDateTime));
            parameters.addValue("type", give.getType());
            parameters.addValue("amount", give.getAmount());
            parameters.addValue("amountType", give.getAmountType());
            parameters.addValue("description", give.getDescription());
            parameters.addValue("img_url", giveImageUrl);
            parameters.addValue("location", give.getLocation());
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

    @Override
    public Give getGiveById(Long id) {
        try {
            return jdbc.queryForObject(SELECT_GIVE_BY_ID_QUERY, Collections.singletonMap("id", id), giveRowMapper);
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No give found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }

    @Override
    public Give updateGive(Long userId, Give give) {
        try {
            jdbc.getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS=0");
            jdbc.update(UPDATE_GIVE_QUERY, getGiveSqlParameterSource(userId, give));
            jdbc.getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS=1");
            return get(give.getId());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No Give found by id: " + give.getId());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public void deleteGive(Long id) {
        try {
            String deleteQuery = "DELETE FROM harvest.Give WHERE id = ?";
            int rowsAffected = jdbc.getJdbcTemplate().update(deleteQuery, id);
            if (rowsAffected == 0) {
                throw new ApiException("No Give found by id: " + id);
            }
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No Give found by id: " + id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private SqlParameterSource getGiveSqlParameterSource(Long userId, Give give) {
        return new MapSqlParameterSource()
                .addValue("id", give.getId())
                .addValue("type", give.getType())
                .addValue("amount", give.getAmount())
               // .addValue("amountType", give.getAmountType())
                .addValue("description", give.getDescription())
                .addValue("location", give.getLocation())
                .addValue("users_give_id", userId);
    }


    private String setGiveImageUrl(String type) {
        return fromCurrentContextPath().path("/user/image/" + type + ".png").toUriString();
    }

    private void saveImage(String email, MultipartFile image) {
        Path fileStorageLocation = Paths.get(System.getProperty("user.home") + "/Downloads/images/").toAbsolutePath().normalize();
        if(!Files.exists(fileStorageLocation)){
            try {
                Files.createDirectories(fileStorageLocation);
            }catch (Exception exception){
                log.error(exception.getMessage());
                throw new ApiException("Unable to create directories to save image");
            }
            log.info("Created directories: {}", fileStorageLocation);
        }
        try{
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(email + ".png"), REPLACE_EXISTING);
        } catch (IOException exception){
            throw  new ApiException(exception.getMessage());
        }
        log.info("File saved in: {} folder", fileStorageLocation);
    }
}
