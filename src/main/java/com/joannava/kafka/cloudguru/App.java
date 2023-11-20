package com.joannava.kafka.cloudguru;

import com.joannava.kafka.katas.TopologyExecutor;
import com.joannava.kafka.katas.filters.SimpleFilterTopology;

public class App {

    public static void main(String[] args) {

        var topology = new InventoryTopology();

        TopologyExecutor executor = new TopologyExecutor("inventory_purchases", topology.build());

        executor.execute();
    }
}
