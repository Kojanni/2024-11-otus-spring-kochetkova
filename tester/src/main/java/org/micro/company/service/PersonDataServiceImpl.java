package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDataServiceImpl implements PersonDataService {

    @Override
    public Person savePerson(Person person) {
        person.setId(1L);
        return person;
    }
}
