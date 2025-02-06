package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonDataServiceImpl implements PersonDataService {
    private static final List<Person> persons = new ArrayList<>();

    @Override
    public Person savePerson(Person person) {
        person.setId((long) (persons.size() + 1));
        persons.add(person);

        return person;
    }
}
