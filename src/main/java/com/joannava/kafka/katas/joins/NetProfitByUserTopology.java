package com.joannava.kafka.katas.joins;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;
import org.apache.kafka.streams.kstream.ValueJoiner;

import com.joannava.kafka.katas.SampleAnalyticsStreamTopology;
import com.joannava.kafka.katas.model.Customer;
import com.joannava.kafka.katas.model.CustomerWithTransaction;
import com.joannava.kafka.katas.model.Transaction;
import com.joannava.kafka.katas.serdes.JacksonSerdes;

public class NetProfitByUserTopology extends SampleAnalyticsStreamTopology {

        @Override
        public Topology build() {
                KStream<String, Customer> customers = getCustomersStream();

                KTable<Integer, Customer> accountCustomers = customers.flatMap((key, customer) -> {
                        return customer.getAccounts().stream()
                                        .map(account -> KeyValue.pair(account, customer))
                                        .collect(Collectors.toSet());
                })

                               // .repartition(Repartitioned.with(Serdes.Integer(), new JacksonSerdes<>(Customer.class))
                                 //               .withName("account_customers"))
                                .toTable(Materialized.with(Serdes.Integer(), new JacksonSerdes<>(Customer.class)));

                KStream<Integer, Transaction> accountTransactions = getTransactionsStream();

                ValueJoiner<Transaction, Customer, CustomerWithTransaction> valueJoiner = (transaction,
                                customer) -> CustomerWithTransaction.builder().customer(customer)
                                                .transaction(transaction).build();

                KStream<Integer, CustomerWithTransaction> customerTransaction = accountTransactions.join(
                                accountCustomers,
                                valueJoiner,
                                Joined.with(Serdes.Integer(), new JacksonSerdes<>(Transaction.class),
                                                new JacksonSerdes<>(Customer.class)));

                customerTransaction.map((key, value) -> KeyValue.pair(value.getCustomer().getUsername(),
                                value.getTransaction().getTransactionCode().equals("sell")
                                                ? value.getTransaction().getTotal()
                                                : value.getTransaction().getTotal().negate()))

                                .groupByKey(Grouped.with(Serdes.String(), new JacksonSerdes<>(BigDecimal.class)))
                                .reduce((a, b) -> a.add(b))
                                .toStream()
                                .to("net_profit_by_user",
                                                Produced.with(Serdes.String(), new JacksonSerdes<>(BigDecimal.class)));

                return builder.build();

        }

}
