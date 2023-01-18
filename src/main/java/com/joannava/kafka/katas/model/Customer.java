package com.joannava.kafka.katas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties({"_id","birthdate","tier_and_details"})
public class Customer {
    private final String username;
    private final String name;
    private final String address;
    private final String email;
    private final boolean active;
    private final List<Integer> accounts;
}
