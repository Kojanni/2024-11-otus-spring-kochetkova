package org.micro.company.dao;

import org.micro.company.dto.GenreEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

    Optional<GenreEntity> findByName(String name);

    @Override
    List<GenreEntity> findAll();
}
