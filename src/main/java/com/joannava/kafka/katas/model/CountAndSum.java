package com.joannava.kafka.katas.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class CountAndSum {
    private final long count;
    private final BigDecimal sum;
}
