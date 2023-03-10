package com.joannava.kafka.katas.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.streams.KeyValue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {

    //Adapting JSON from Mongo to be sent to kafka
    public static List<KeyValue<Integer,String>> extractTransactionsFromParent(String mongoJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode root = mapper.readValue(mongoJson, ObjectNode.class);
            Integer accountId = root.get("account_id").asInt();

            ObjectReader reader = mapper
                    .readerFor(new TypeReference<List<ObjectNode>>() {
                    });

            List<ObjectNode> list = reader.readValue(root.get("transactions"));

            return  list.stream()
                    .map((node) -> {
                        JsonNode date = node.get("date").get("$date");
                        node.remove("date");
                        node.put("date", date.asText(""));
                        node.put("accountId", accountId).toString();
                        return KeyValue.pair(accountId,node.toString());
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return Collections.emptyList();
        }

    }
}
