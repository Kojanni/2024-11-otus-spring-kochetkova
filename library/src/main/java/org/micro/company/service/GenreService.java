package org.micro.company.service;

import org.micro.company.dto.GenreEntity;

import java.util.List;

public interface GenreService {

    List<GenreEntity> findAll();

    GenreEntity findByName(String genreName);

    GenreEntity save(String genreName);
}
