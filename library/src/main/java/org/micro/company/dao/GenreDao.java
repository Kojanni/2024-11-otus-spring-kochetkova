package org.micro.company.dao;

import org.micro.company.dto.GenreEntity;

import java.util.List;

public interface GenreDao {
    List<GenreEntity> findAll();
    GenreEntity findByName(String name);
    GenreEntity save(GenreEntity genre);
}
