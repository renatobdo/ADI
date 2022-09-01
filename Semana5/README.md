## Instalação do zookeeper 3.4.12

Dentro da pasta descompactada do zookeeper criar uma pasta chamada logs

Dentro da pasta conf renomear o arquivo zoo_sample para zoo.cfg

Alterar o caminho dataDir dentro do arquivo zoo.cfg 

dataDir=/mnt/c/testes/zookeeper-3.4.12/logs

Abrir um novo terminal e dentro da pasta bin executar o servidor com o comando

./zkServer.sh start

Abrir outro terminal e executar dentro da pasta bin o cliente

zkCli.sh 
