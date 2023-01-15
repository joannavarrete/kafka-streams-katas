package com.joannava.kafka.katas.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class Transaction {

    private final int accountId;
 
    private final Date date;
    private final int amount;

    @JsonProperty("transaction_code")
    private final String transactionCode;

    private final String symbol;

    private final float price;

    private final float total;

}
