<a name="readme-top"></a>
# Apache Kafka Streams katas

Katas/exercises to learn or practice Apache Kafka Streams API

## Motivation

When learning Kafka Streams is often difficult to find open data-streams to pracitce with.

This app uses [Mongodb Sample database](https://github.com/neelabalan/mongodb-sample-dataset) to provide events to filter, join, aggregate, window, etc.

## Prerequisites

- [MongoDB](https://www.mongodb.com/)
- An Apache Kafka Cluster (local, docker, cloud).
- [A Graphical interface for kafka is nice to have](https://github.com/provectus/kafka-ui)
- Java 17 or higher

## Usage
1. First [import](https://www.mongodb.com/docs/database-tools/mongoimport/) the [sample_analytics](https://github.com/neelabalan/mongodb-sample-dataset/tree/main/sample_analytics) collections in a "sample_analytics" database.
2. Configure your connection Strings in app.properties
3. Then produce Transactions from Mongodb to Kafka with the [Producer](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/producers/ProducerApp.java)
4. You're ready to start the katas
5. By default Kafka logs(data) are stored in /tmp/a_kafka_folder so if you want to restart fresh just delete it.

## Example of the event JSON produced from MongoDB to Kafka

```JSON
{
   "amount":4345,
   "transaction_code":"sell",
   "symbol":"ibm",
   "price":"180.2130283892771558384993113577365875244140625",
   "total":"783025.6083514092421182795078",
   "date":"2013-05-23T00:00:00Z",
   "accountId":"218657"
}
```


**Account Id** is the **key** of the "transactions" **topic**. We are going to group by it and it's more convenient. 


## Katas

### Stateless Operations

- **Filters**
  - Create a new Topology filters Transactions by (accountId, transactionCode,etc) and writes to a new filtered topic.
  - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/filters/SimpleFilterTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/filters/SimpleFilterTopologyTest.java)

- **Branches**
  - Create a new Topology that branches transaction by code ("sell", "buy") and writes two topics, one for each code.
  - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/branches/BranchTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/branches/BranchTopologyTest.java)

### Stateful Operations

#### Aggregations

```
Grouping is a prerequisite for aggregating
```

- **Count** 
  - Count all Transactions made for an Account
  - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/aggregations/CountTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/aggregations/CountTopologyTest.java)

- **Sum**
   - Sum all sellings earnings by Account
   - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/aggregations/SumSellsTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/aggregations/SumSellsTopologyTest.java)

- **Net profit**
   - Calculate the net profit (selling - buying) by Account
   - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/aggregations/NetProfitTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/aggregations/NetProfitTopologyTest.java)

- **Average**
   - Calculate the average price from symbol (ibm,ebay,etc..)
   - [Solution](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/main/java/com/joannava/kafka/katas/aggregations/SymbolAverageTopology.java) -- [Solution Test](https://github.com/joannavarrete/kafka-streams-katas/blob/main/src/test/java/com/joannava/kafka/katas/aggregations/SymbolAverageTopologyTest.java)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
### Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
### License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Joan Navarrete - [@joannava](https://twitter.com/joannava) - joan.navarrete.sanchez@gmail.com


