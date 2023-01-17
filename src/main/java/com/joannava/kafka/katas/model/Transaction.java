package com.joannava.kafka.katas.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class Transaction {

    @JsonProperty("transaction_code")
    private final String transactionCode;
    
    private final int accountId;
    private final Date date;
    private final int amount;
    private final String symbol;
    private final BigDecimal price;
    private final BigDecimal total;

}
