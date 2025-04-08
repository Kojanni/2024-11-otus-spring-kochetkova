package org.micro.company.dao.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.GenreDao;
import org.micro.company.dto.GenreEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<GenreEntity> findAll() {
        return jdbcOperations.query("select * from genre", new GenreMapper());
    }

    @Override
    public GenreEntity findByName(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);

        GenreEntity genreEntity;
        try {
            genreEntity = jdbcOperations.queryForObject("select * from genre where name = :name",
                    params, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            genreEntity = save(GenreEntity.builder().name(name).build());
        }
        return genreEntity;
    }

    @Override
    public GenreEntity save(GenreEntity genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update("insert into genre (name) values(:name)", params, keyHolder);

        return findByName(genre.getName());
    }

    private static class GenreMapper implements RowMapper<GenreEntity> {

        @Override
        public GenreEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new GenreEntity(id, name);
        }
    }
}
