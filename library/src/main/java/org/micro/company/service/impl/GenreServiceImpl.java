package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.micro.company.dao.GenreDao;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    @Transactional(readOnly = true)
    public List<GenreEntity> findAll() {
        return genreDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GenreEntity findByName(String genreName) {

        return genreDao.findByName(genreName);
    }

    @Override
    @Transactional
    public GenreEntity save(String genreName) {
        GenreEntity genreEntity = findByName(genreName);
        if (genreEntity != null) {
            log.debug("Genre: " + genreName + " already exists.");
            return genreEntity;
        }
        return genreDao.save(GenreEntity.builder()
                .name(genreName)
                .build());
    }
}
