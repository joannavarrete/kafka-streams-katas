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

    private final Integer accountId;
 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-ddTHH:mm:ss.SSSZ")
    private final Date date;
    private final Integer amount;

    @JsonProperty("transaction_code")
    private final String transactionCode;

    private final String symbol;

    private final Float price;

    private final Float total;

}
