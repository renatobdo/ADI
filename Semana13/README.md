## Introdução ao Kafka

Passo a passo para instalação utilizando o wsl:
https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-windows

Editei o bash no wsl

## Edit .bashrc
nano ~/.bashrc

## Add PATH
Add PATH="$PATH:/your/path/to/your/kafka/bin"
No meu caso: PATH="$PATH:/mnt/c/testes/kafka_2.13-3.3.1/bin"

# para iniciar o zookeeper:
zookeeper-server-start.sh /mnt/c/testes/kafka_2.13-3.3.1/config/zookeeper.properties

# para iniciar o kafka:
kafka-server-start.sh /mnt/c/testes/kafka_2.13-3.3.1/config/server.properties

# server.properties e zookeeper.properties
 modifiquei o server.properties e zookeeper.properties respectivamente:
log.dirs=/mnt/c/testes/kafka_2.13-3.3.1/logs
dataDir=/mnt/c/testes/kafka_2.13-3.3.1/data_zookeeper


## comandos para um tópico
(https://www.conduktor.io/kafka/kafka-topics-cli-tutorial)

## lista os tópicos:

kafka-topics.sh --bootstrap-server localhost:9092 --list

## cria o primeiro tópico:

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic primeiro_topico

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic segundo_topico


## Aqui irá dar erro porque o fator de replicação é maior do que a quantidade de brokers:

kafka-topics.sh --bootstrap-server localhost:9092 --create --topic terceiro_topico --partitions 3 --replication-factor 2



// WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could 
// collide. To avoid issues it is best to use either, but not both.
//Error while executing topic command : Replication factor: 2 larger than available brokers: 1.
//[2022-10-21 13:55:10,683] ERROR org.apache.kafka.common.errors.InvalidReplicationFactorException: 
//Replication factor: 2 larger than available brokers: 1.


kafka-topics.sh --bootstrap-server localhost:9092 --create --topic terceiro_topico --partitions 3 --replication-factor 1


kafka-topics.sh --bootstrap-server localhost:9092 --list

kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic primeiro_topico

## deletando tópicos:

kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic c

