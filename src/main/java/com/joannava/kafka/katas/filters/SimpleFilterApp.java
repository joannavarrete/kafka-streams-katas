package com.joannava.kafka.katas.filters;

import com.joannava.kafka.katas.TopologyExecutor;

public class SimpleFilterApp {
    public static void main(String[] args) {

        SimpleFilterTopologyBuilder builder = new SimpleFilterTopologyBuilder();

        TopologyExecutor executor = new TopologyExecutor("simple_filter", builder.build());

        executor.execute();
    }
}
