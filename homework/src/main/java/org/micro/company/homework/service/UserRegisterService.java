package org.micro.company.homework.service;

import org.micro.company.homework.domain.Person;

public interface UserRegisterService {
    Person registerUser(Person person);

    Person requestUserByConsole();
}
