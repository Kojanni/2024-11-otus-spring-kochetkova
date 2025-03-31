package org.micro.company.dao.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.BookDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
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
import java.util.Objects;

import static org.micro.company.dao.impl.AuthorDaoImpl.AUTHOR_ID;


@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookDaoImpl implements BookDao {

    public static final String TITLE = "title";
    public static final String BOOK_ID = "bookId";
    public static final String AUTHOR_NAME = "authorName";
    public static final String AUTHOR_SURNAME = "authorSurname";
    public static final String AUTHOR_MIDDLE_NAME = "authorMiddleName";
    public static final String GENRE_ID = "genreId";
    public static final String GENRE_NAME = "genreName";

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public BookEntity findById(Long id) {
        try {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        return jdbcOperations.queryForObject("select b.id bookId, b.title, b.genreId, b.authorId,  " +
                        "a.name authorName, a.surname authorSurname, a.middleName authorMiddleName, " +
                        "g.name genreName " +
                        "from book b " +
                        "left join author a on b.authorId = a.id " +
                        "left join genre g on b.genreId = g.id " +
                        "where b.id = :id",
                params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookEntity> findAll() {
        return jdbcOperations.query("select b.id bookId, b.title, b.genreId, b.authorId,  " +
                        "a.name authorName, a.surname authorSurname, a.middleName authorMiddleName, " +
                        "g.name genreName " +
                        "from book b " +
                        "left join author a on b.authorId = a.id " +
                        "left join genre g on b.genreId = g.id",
                new BookMapper());
    }

    @Override
    public BookEntity findByTitleAndAuthor(String title, AuthorEntity author) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(TITLE, title);
        params.put(AUTHOR_ID, author.getId());

        return jdbcOperations.queryForObject("select b.id bookId, b.title, b.genreId, b.authorId, " +
                        "a.name authorName, a.surname authorSurname, a.middleName authorMiddleName, " +
                        "g.name genreName " +
                        "from book b " +
                        "left join author a on b.authorId = a.id " +
                        "left join genre g on b.genreId = g.id " +
                        "where b.title = :title " +
                        "and b.authorId = :authorId",
                params, new BookMapper());
    }

    @Override
    public List<BookEntity> findByAuthor(AuthorEntity author) {
        Map<String, Object> params = new HashMap<>(2);
        params.put(AUTHOR_ID, author.getId());

        return jdbcOperations.query("select b.id bookId, b.title, b.genreId, b.authorId, " +
                        "a.name authorName, a.surname authorSurname, a.middleName authorMiddleName, " +
                        "g.name genreName " +
                        "from book b " +
                        "left join author a on b.authorId = a.id " +
                        "left join genre g on b.genreId = g.id " +
                        "where b.authorId = :authorId",
                params, new BookMapper());
    }

    @Override
    public BookEntity save(BookEntity book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(BookDaoImpl.TITLE, book.getTitle());
        params.addValue(GENRE_ID, book.getGenre().getId());
        params.addValue(AUTHOR_ID, book.getAuthor().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update("insert into book (title, genreid, authorId) values(:title, :genreId, :authorId)", params, keyHolder);

        return findById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
    }

    @Override
    public void deleteById(Long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);

        jdbcOperations.update("delete from book where id = :id", params);
    }

    @Override
    public Long count() {
        return jdbcOperations.queryForObject("select count(*) from book", new HashMap<>(1), Long.class);
    }

    private static class BookMapper implements RowMapper<BookEntity> {

        @Override
        public BookEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs.wasNull()) {
                return null;
            }
            return BookEntity.builder()
                    .id(rs.getLong(BOOK_ID))
                    .title(rs.getString(BookDaoImpl.TITLE))
                    .author(AuthorEntity.builder()
                            .id(rs.getLong(AUTHOR_ID))
                            .name(rs.getString(AUTHOR_NAME))
                            .surname(rs.getString(AUTHOR_SURNAME))
                            .middleName(rs.getString(AUTHOR_MIDDLE_NAME))
                            .build())
                    .genre(GenreEntity.builder()
                            .id(rs.getLong(GENRE_ID))
                            .name(rs.getString(GENRE_NAME))
                            .build())
                    .build();
        }
    }
}
