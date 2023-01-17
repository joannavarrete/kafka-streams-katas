package com.joannava.kafka.katas.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class CountAndSum {
    private final long count;
    private final float sum;
}
