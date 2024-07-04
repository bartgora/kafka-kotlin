# Kotlin Kafka

A sample Kafka producer and consumer implemented in kotlin.

To start:
Open both projects (you may import whole folder as a project into Intelij Idea)
run:
```shell
docker compose up -d
```

run:</br>
```shell
docker exec kafka1 /bin/kafka-topics --bootstrap-server localhost:9092 --create --topic fun-with-kafka-topic --partitions 3 --replication-factor 2
```

That will configure kafka to use topic named ' fun-with-kafka-topic' with 3 partitions, and replication-factor= 1.</br>
Replication factor must be set to 1, because we have only one broker (kafka service in docker compose is called broker).</br>
If we had more than one brokers, then we could increase the number. Hoverer it doesn't have to be equal to number of brokers</br>

You can find more info in official documentation.

Now run the producer app, and consumer app.
then call for example:
```http request
POST http://localhost:8083/send
Content-Type: application/json

[
  {
    "key": "1",
    "value": "The Wither 3"
  },
  {
    "key": "2",
    "value": "Cyberpunk 2077"
  },
  {
    "key": "3",
    "value": "Splinter Cell"
  },
  {
    "key": "4",
    "value": "Settlers"
  }
]
```
Now, let's look at the consumer logs:
```shell
Reading..
Reading..
Reading..
Partition read:1 Key: 4 Value:Settlers Offset: 1
Partition read:2 Key: 2 Value:Cyberpunk 2077 Offset: 2
Partition read:2 Key: 3 Value:Splinter Cell Offset: 3
Partition read:0 Key: 1 Value:The Wither 3 Offset: 1
Reading..
Reading..

```

The consumer got data from all 3 partitions.

Let's have a look at producer logs:
```shell
topic: fun-with-kafka-topic Partition: 1 Offset: 1 timestamp: 1719604072041
topic: fun-with-kafka-topic Partition: 2 Offset: 2 timestamp: 1719604072041
topic: fun-with-kafka-topic Partition: 2 Offset: 3 timestamp: 1719604072041
topic: fun-with-kafka-topic Partition: 0 Offset: 1 timestamp: 1719604072040

```

Now you can try to add another consumer, just by running consumer app with different port.</br>
