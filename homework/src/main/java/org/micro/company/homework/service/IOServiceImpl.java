package org.micro.company.homework.service;

import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private Scanner sc = new Scanner(System.in);

    public IOServiceImpl() {
    }

    @Override
    public String read() {
        return sc.nextLine();
    }

    @Override
    public int readInt() {
        return sc.nextInt();
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }
}
