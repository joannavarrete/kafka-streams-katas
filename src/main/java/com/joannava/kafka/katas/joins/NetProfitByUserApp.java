package com.joannava.kafka.katas.joins;

import com.joannava.kafka.katas.TopologyExecutor;

public class NetProfitByUserApp {
    public static void main(String[] args) {

        NetProfitByUserTopology topology = new NetProfitByUserTopology();

        TopologyExecutor executor = new TopologyExecutor("user_profit_condemorrer", topology.build());

        executor.execute();

    }
}
