package com.joannava.kafka.katas.aggregations;

import com.joannava.kafka.katas.TopologyExecutor;

public class CountApp {
    public static void main(String[] args) {
        CountTopology topology = new CountTopology();

        TopologyExecutor executor = new TopologyExecutor("count_app", topology.build());

        executor.execute();
    }
}
