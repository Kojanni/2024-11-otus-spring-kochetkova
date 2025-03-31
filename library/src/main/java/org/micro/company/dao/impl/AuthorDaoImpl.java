package org.micro.company.dao.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.AuthorDao;
import org.micro.company.dto.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorDaoImpl implements AuthorDao {

    public static final String AUTHOR_ID = "authorId";
    public static final String SURNAME = "surname";
    public static final String NAME = "name";
    public static final String MIDDLE_NAME = "middleName";

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<AuthorEntity> findAll() {
        return jdbcOperations.query("select * from author", new AuthorMapper());
    }

    @Override
    public AuthorEntity findByFullName(String surname, String name, String middleName) {
        try {
        final Map<String, Object> params = new HashMap<>(3);
        params.put(NAME, name);
        params.put(SURNAME, Objects.requireNonNullElse(surname, ""));
        params.put(MIDDLE_NAME, Objects.requireNonNullElse(middleName, ""));

        return jdbcOperations.queryForObject("select * from author where name = :name " +
                        "and surname = :surname " +
                        "and middleName = :middleName",
                params, new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public AuthorEntity save(AuthorEntity author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(NAME, author.getName());
        params.addValue(MIDDLE_NAME, Objects.requireNonNullElse(author.getMiddleName(), ""));
        params.addValue(SURNAME, Objects.requireNonNullElse(author.getSurname(), ""));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update("insert into author (name, middleName, surname) values(:name, :middleName, :surname)", params, keyHolder);

        return findByFullName(author.getSurname(), author.getName(), author.getMiddleName());
    }

    private static class AuthorMapper implements RowMapper<AuthorEntity> {

        @Override
        public AuthorEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString(NAME);
            String middleName = rs.getString(MIDDLE_NAME);
            String surname = rs.getString(SURNAME);

            return new AuthorEntity(id, name, middleName, surname);
        }
    }
}
