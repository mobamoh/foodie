## 1) Start Zookeeper
- docker-compose -f common.yml -f zookeeper.yml up
- echo ruok | nc localhost 2181 -> imok

## 2) Start Kafka Cluster
- docker-compose -f common.yml -f kafka_cluster.yml up

## 3) Init Kafka Cluster
- docker-compose -f common.yml -f init_kafka.yml up

## 4) Kafka Manager UI
- localhost:9000
- create cluster -> foodie-cluster -> zookeeper:2181