package org.micro.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.micro.company.dao.AuthorDao;
import org.micro.company.dto.AuthorEntity;
import org.micro.company.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private static final int SURNAME_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int MIDDLE_NAME_INDEX = 2;

    @Override
    public List<AuthorEntity> findAll() {
        return authorDao.findAll();
    }

    @Override
    public AuthorEntity findByFullName(String authorFullName) {
        String[] fullName = authorFullName.split(" ");
        String surname = fullName[SURNAME_INDEX];
        String name = fullName[NAME_INDEX];
        String middleName = fullName.length > 2 ? fullName[MIDDLE_NAME_INDEX] : null;

        return Objects.requireNonNullElse(
                authorDao.findByFullName(surname, name, middleName),
                save(surname, name, middleName)
        );
    }

    @Override
    public AuthorEntity save(String surname, String name, String middleName) {
        return authorDao.save(AuthorEntity.builder()
                .surname(surname)
                .name(name)
                .middleName(middleName)
                .build());
    }
}
