package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSystemServiceImpl implements TestSystemService {
    private final TestingService testingService;
    private final UserRegisterService userRegisterService;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void startToTest() {
        Person user = userRegisterService.requestUserByConsole();
        userRegisterService.registerUser(user);

        int testResult = testingService.runTests();
        testingService.saveResult(user, testResult);
    }
}