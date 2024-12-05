package org.micro.company.homework.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.homework.domain.Person;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSystemServiceImpl implements TestSystemService {
    private final TestingService testingService;
    private final UserRegisterService userRegisterService;

    @Override
    public void startToTest() {
        Person user = userRegisterService.requestUserByConsole();
        userRegisterService.registerUser(user);

        int testResult = testingService.runTests();
        testingService.saveResult(user, testResult);
    }
}
