package org.micro.company.homework.service;

import org.micro.company.homework.domain.Person;
import org.micro.company.homework.domain.TestResult;

public interface TestingService {
    int runTests();

    TestResult saveResult(Person user, int testResult);
}
