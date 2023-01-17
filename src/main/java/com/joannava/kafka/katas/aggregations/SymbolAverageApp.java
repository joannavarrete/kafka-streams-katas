package com.joannava.kafka.katas.aggregations;

import com.joannava.kafka.katas.TopologyExecutor;

public class SymbolAverageApp {
    public static void main(String[] args) {

        SymbolAverageTopology topology = new SymbolAverageTopology();

        TopologyExecutor executor = new TopologyExecutor("average_app", topology.build());

        executor.execute();
    }
}
