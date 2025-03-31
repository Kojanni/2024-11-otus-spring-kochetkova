package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.GenreDao;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<GenreEntity> findAll() {
        return genreDao.findAll();
    }

    @Override
    public GenreEntity findByName(String genreName) {

        return genreDao.findByName(genreName);
    }

    @Override
    public GenreEntity save(String genreName) {
        return genreDao.save(GenreEntity.builder()
                .name(genreName)
                .build());
    }
}
