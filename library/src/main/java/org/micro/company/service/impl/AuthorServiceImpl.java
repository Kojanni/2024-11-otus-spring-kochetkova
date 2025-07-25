package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.micro.company.dao.AuthorRepository;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorServiceImpl implements AuthorService {

    public static final String REGEX = " ";
    private final AuthorRepository authorRepository;

    private static final int SURNAME_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int MIDDLE_NAME_INDEX = 2;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorEntity> findByFullName(String authorFullName) {
        String[] fullName = authorFullName.split(REGEX);
        String surname = fullName[SURNAME_INDEX];
        String name = fullName.length > 1 ? fullName[NAME_INDEX] : null;
        String middleName = fullName.length > 2 ? fullName[MIDDLE_NAME_INDEX] : null;

        return authorRepository.findBySurnameAndNameAndMiddleName(surname, name, middleName);
    }

    @Override
    @Transactional
    public AuthorEntity save(String surname, String name, String middleName) {
        Optional<AuthorEntity> authorEntity = authorRepository.findBySurnameAndNameAndMiddleName(surname, name, middleName);
        return authorEntity.orElseGet(() -> authorRepository.save(AuthorEntity.builder()
                .surname(surname)
                .name(name)
                .middleName(middleName)
                .build()));
    }
}
