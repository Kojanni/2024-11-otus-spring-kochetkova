package org.micro.company.homework;

import org.micro.company.homework.service.TestingService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        TestingService runnerService = context.getBean(TestingService.class);
        runnerService.runTests();
    }
}