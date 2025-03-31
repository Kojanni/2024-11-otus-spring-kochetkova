package org.micro.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {
    private Long id;
    private String name;
    private String middleName;
    private String surname;
}
