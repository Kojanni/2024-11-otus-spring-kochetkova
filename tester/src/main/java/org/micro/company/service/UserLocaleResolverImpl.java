package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserLocaleResolverImpl implements UserLocaleResolver {
    @Override
    public String changeLocale(String lang) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(lang));
        return "Locale changed to " + lang;
    }
}
