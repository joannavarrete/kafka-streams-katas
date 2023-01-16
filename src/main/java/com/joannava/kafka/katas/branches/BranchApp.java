package com.joannava.kafka.katas.branches;

import com.joannava.kafka.katas.TopologyExecutor;

public class BranchApp {

    public static void main(String[] args) {
        BranchTopology topology = new BranchTopology();

        TopologyExecutor executor = new TopologyExecutor("branch_app", topology.build());

        executor.execute();

    }
}
