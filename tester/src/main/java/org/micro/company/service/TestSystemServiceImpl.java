package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSystemServiceImpl implements TestSystemService {
    private final TestingService testingService;
    private final UserRegisterService userRegisterService;
    private final UserLocaleResolver userLocaleResolver;
    private final IOService ioService;
    private final MessageSource messageSource;


    @Override
    public void startToTest() {
        ioService.write(messageSource.getMessage(
                "enter_locale",
                new Object[]{},
                "Please en or ru",
                LocaleContextHolder.getLocale()));
        String lang = ioService.read();
        userLocaleResolver.changeLocale(lang);
        Person user = userRegisterService.requestUserByConsole();
        userRegisterService.registerUser(user);

        int testResult = testingService.runTests();
        testingService.saveResult(user, testResult);
    }
}