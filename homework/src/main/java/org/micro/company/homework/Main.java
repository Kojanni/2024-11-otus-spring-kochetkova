package org.micro.company.homework;

import org.micro.company.homework.config.AppConfig;
import org.micro.company.homework.service.TestSystemService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TestSystemService testSystemService = context.getBean(TestSystemService.class);
        testSystemService.startToTest();
    }
}