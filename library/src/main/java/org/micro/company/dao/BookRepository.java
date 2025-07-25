package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;
import org.micro.company.dto.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<BookEntity, Long> {

    @EntityGraph(value = "book.author.genre")
    Optional<BookEntity> findByTitleAndAuthor(String title, AuthorEntity author);

    @EntityGraph(value = "book.author.genre", type = EntityGraph.EntityGraphType.FETCH)
    List<BookEntity> findAll();

    @EntityGraph(value = "book.author.genre")
    List<BookEntity> findByAuthor(AuthorEntity author);

    @Override
    @Modifying
    @Query("delete BookEntity b where b.id = :id")
    void deleteById(@Param("id") Long id);
}
