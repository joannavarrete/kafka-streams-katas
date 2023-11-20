package com.joannava.kafka.katas.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class CustomerWithTransaction {
    private final Customer customer;
    private final Transaction transaction;
}
