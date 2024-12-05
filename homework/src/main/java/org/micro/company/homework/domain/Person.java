package org.micro.company.homework.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
}
