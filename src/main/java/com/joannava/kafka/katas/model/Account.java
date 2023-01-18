package com.joannava.kafka.katas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Account{
    
    @JsonProperty("transaction_code")
    private final int id;
    private final int limit;
    private final List<String> products;
}