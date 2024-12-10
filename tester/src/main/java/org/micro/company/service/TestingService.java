package org.micro.company.service;

import org.micro.company.domain.Person;
import org.micro.company.domain.TestResult;

public interface TestingService {
    int runTests();
    TestResult saveResult(Person user, int testResult);
}
