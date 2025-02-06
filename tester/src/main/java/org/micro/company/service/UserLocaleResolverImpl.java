package org.micro.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserLocaleResolverImpl implements UserLocaleResolver {
    private final Set<String> existingLocales = Set.of("ru", "en");

    @Value("${spring.jackson.locale:ru}")
    private String defaultLocale;

    @Override
    public String changeLocale(String lang) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(lang));
        return "Locale changed to " + lang;
    }

    @Override
    public String getLocale() {
        String locale = LocaleContextHolder.getLocale().toLanguageTag();
        if (existingLocales.contains(locale)) {
            return locale;
        }
        return defaultLocale;
    }
}
