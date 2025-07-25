package org.micro.company.dao;

import org.micro.company.dto.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findBySurnameAndNameAndMiddleName(String surname, String name, String middleName);

    @Override
    List<AuthorEntity> findAll();
}
