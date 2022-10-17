## Introdução ao Kafka

Passo a passo para instalação utilizando o wsl:
https://www.conduktor.io/kafka/how-to-install-apache-kafka-on-windows

Editei o bash no wsl

# Edit .bashrc
nano ~/.bashrc

# Add PATH="$PATH:/your/path/to/your/kafka/bin"
# No meu caso: PATH="$PATH:/mnt/c/testes/kafka_2.13-3.3.1/bin"

# para iniciar o zookeeper:
zookeeper-server-start.sh /mnt/c/testes/kafka_2.13-3.3.1/config/zookeeper.properties

# para iniciar o kafka:
kafka-server-start.sh /mnt/c/testes/kafka_2.13-3.3.1/config/server.properties

# modifiquei o server.properties e zookeeper.properties respectivamente:
log.dirs=/mnt/c/testes/kafka_2.13-3.3.1/logs
dataDir=/mnt/c/testes/kafka_2.13-3.3.1/data_zookeeper





## Introdução ao SpringBoot e criação de APIs REST
