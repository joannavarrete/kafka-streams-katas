package com.joannava.kafka.katas.aggregations;

import com.joannava.kafka.katas.TopologyExecutor;

public class NetProfitApp {
    public static void main(String[] args) {
        NetProfitTopology topology = new NetProfitTopology();

        TopologyExecutor executor = new TopologyExecutor("net_profit_app", topology.build());

        executor.execute();
    }
}
