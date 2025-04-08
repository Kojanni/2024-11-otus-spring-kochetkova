package org.micro.company.service;

import org.micro.company.dto.AuthorEntity;

import java.util.List;

public interface AuthorService {

    List<AuthorEntity> findAll();

    AuthorEntity findByFullName(String authorFullName);

    AuthorEntity save(String surname, String name, String middleName);
}
