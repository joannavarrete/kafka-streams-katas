package com.joannava.kafka.katas.aggregations;

import com.joannava.kafka.katas.TopologyExecutor;

public class SumSellsApp {
    public static void main(String[] args) {
        SumSellsTopology topology = new SumSellsTopology();

        TopologyExecutor executor = new TopologyExecutor("sum_sells_app", topology.build());

        executor.execute();
    }
}
