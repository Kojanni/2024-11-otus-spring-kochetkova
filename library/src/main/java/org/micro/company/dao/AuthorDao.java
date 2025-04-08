package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;

import java.util.List;

public interface AuthorDao {

    List<AuthorEntity> findAll();
    AuthorEntity findByFullName(String surname, String name, String middleName);

    AuthorEntity save(AuthorEntity author);
}
