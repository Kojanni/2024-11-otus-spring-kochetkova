package org.micro.company.shell;

import lombok.RequiredArgsConstructor;
import org.micro.company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import static org.micro.company.shell.MethodKey.*;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ShellComponent
public class TestShell {

    public static final String INSTRUCTION_TEXT = "Test instruction\n" +
            "1. Choose locale or used default(ru)\n2. Registation user\n3. Start to test\n" +
            "OR u can run test mode\nGood luck!";
    private boolean isRegistrated = false;

    private final TestSystemService testSystemService;
    private final TestingService testingService;
    private final UserRegisterService userRegisterService;
    private final MessageSource messageSource;
    private final UserLocaleResolver userLocaleResolver;

    @ShellMethod(key = {INSTRUCTION, INSTRUCTION_SHORT}, value = "Test instruction")
    public String instruction() {
        return INSTRUCTION_TEXT;
    }

    @ShellMethod(key = LOCALE, value = "Change locale: en or ru")
    public String locale(@ShellOption(value = {"-locale", "-l"}, defaultValue = "ru") String locale) {
        return userLocaleResolver.changeLocale(locale);
    }

    @ShellMethod(key = {REGISTATION, REGISTATION_SHORT}, value = "registration user: firstName, lastName")
    public String registration(@ShellOption(value = {"-firstName", "-fn"}) String firstName,
                               @ShellOption(value = {"-lastName", "-ln"}) String lastName) {

        userRegisterService.registerUser(firstName, lastName);
        isRegistrated = true;
        return messageSource.getMessage(
                "user_registration_success",
                new Object[]{},
                "User registration successful",
                LocaleContextHolder.getLocale());
    }

    @ShellMethod(key = {TESTING}, value = "Start testing")
    @ShellMethodAvailability(value = "isTestAvailable")
    public Integer startToTest() {
        return testingService.runTests();
    }

    @ShellMethod(key = {START_TEST_MODE, TEST_MODE}, value = "Start testing")
    public void startTestMode() {
        testSystemService.startToTest();
    }

    private Availability isTestAvailable() {
        if (!isRegistrated) {
            return Availability.unavailable("Error: User not found. Please register first");
        }
        return Availability.available();
    }
}
