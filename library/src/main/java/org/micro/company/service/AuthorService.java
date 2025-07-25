package org.micro.company.service;

import org.micro.company.dto.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findByFullName(String authorFullName);

    AuthorEntity save(String surname, String name, String middleName);
}
