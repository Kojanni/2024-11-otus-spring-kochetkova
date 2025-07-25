package org.micro.company.service;

import org.micro.company.dto.GenreEntity;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<GenreEntity> findAll();

    Optional<GenreEntity> findByName(String genreName);

    GenreEntity save(String genreName);
}
