package org.micro.company.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {
    private final Scanner sc;

    public IOServiceImpl() {
        this.sc = new Scanner(System.in);
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

