package org.micro.company.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestResult {
    private Long id;
    private Long userId;
    private Integer score;
    private LocalDateTime time;
}

