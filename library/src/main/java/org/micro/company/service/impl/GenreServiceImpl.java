package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.micro.company.dao.GenreRepository;
import org.micro.company.dto.GenreEntity;
import org.micro.company.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GenreEntity> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenreEntity> findByName(String genreName) {
        return genreRepository.findByName(genreName);
    }

    @Override
    @Transactional
    public GenreEntity save(String genreName) {
        Optional<GenreEntity> genreEntity = genreRepository.findByName(genreName);
        return genreEntity.orElseGet(() -> genreRepository.save(GenreEntity.builder()
                .name(genreName)
                .build()));
    }
}
