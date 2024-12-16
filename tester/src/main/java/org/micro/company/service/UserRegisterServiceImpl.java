package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.micro.company.domain.Person;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {
    private final PersonDataService personDataService;
    private final IOService ioService;
    private final MessageSource messageSource;

    @Override
    public Person registerUser(Person person) {
        if (person == null) {
            person = requestUserByConsole();
        }
        return personDataService.savePerson(person);
    }

    @Override
    public Person requestUserByConsole() {
        ioService.write(messageSource.getMessage(
                "registration_input_firstname",
                new Object[]{},
                LocaleContextHolder.getLocale()));
        String firstName = ioService.read();

        ioService.write(messageSource.getMessage(
                "registration_input_secondname",
                new Object[]{},
                LocaleContextHolder.getLocale()));
        String lastName = ioService.read();

        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
