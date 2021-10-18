# Kafka

This file contains some instructions for managing kafka servers that I learned in the Kafka course on the Alura platform. If you want to play around with some instructions described here, go to kafka.apache.org and download the binary. Although these commands are present in the documentation, I'm just doing this as a shortcut to them.

- Start Zookeeper
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

- Start Kafka
```
bin/kafka-server-start.sh config/server.properties
```

- Create topic
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TOPIC_NAME
```

- List topics
```
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

- Describe topics
```
bin/kafka-topics.sh --describe --bootstrap-server localhost:9092
```

- Alter topic partition
```
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic TOPIC_NAME --partitions NUM_PARTITIONS
```

- Create producer (console)
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TOPIC_NAME
```

- Create consumer (console)
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TOPIC_NAME --from-beginning
```
