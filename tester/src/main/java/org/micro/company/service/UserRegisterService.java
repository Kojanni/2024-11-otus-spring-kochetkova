package org.micro.company.service;

import org.micro.company.domain.Person;

public interface UserRegisterService {
    Person registerUser(Person person);
    Person requestUserByConsole();
}
