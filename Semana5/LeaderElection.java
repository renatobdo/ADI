package election;

/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Leader Election Implementation in Java
 * https://www.udemy.com/course/distributed-systems-cloud-computing-with-java/
 */
public class LeaderElection implements Watcher {
    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    //O servidor do zookeeper fica constantemente verificando a conexão com o cliente
    //Se o servidor zookeeper não ouvir nada do cliente em um tempo maior que o definido
    // no timeout 3000ms irá considerar o cliente desconectado ou morto
    private static final int SESSION_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";
    private ZooKeeper zooKeeper;
    private String currentZnodeName;

    // NOTE - Don't forget to create the /election ZNode
    public static void main(String[] arg) throws IOException, InterruptedException, KeeperException {
        LeaderElection leaderElection = new LeaderElection();

        leaderElection.connectToZookeeper();
        leaderElection.volunteerForLeadership();
        leaderElection.electLeader();
        leaderElection.run();
        leaderElection.close();
        System.out.println("Disconnected from Zookeeper, exiting application");
    }

    public void volunteerForLeadership() throws KeeperException, InterruptedException {
    	//o prefixo c é de candidato
        String znodePrefix = ELECTION_NAMESPACE + "/c_";
        // o segundo parâmetro é o dado que queremos colocar, o outro parâmetro quer dizer que
        //se desconectarmos do zookeeper o znode será deletado. O nó do znode será 
        String znodeFullPath = zooKeeper.create(znodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("znode name " + znodeFullPath);
        this.currentZnodeName = znodeFullPath.replace("/election/", "");
    }

    public void electLeader() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);

        Collections.sort(children);
        //o nome da sequência do nó mais curto será o primeiro elemento
        String smallestChild = children.get(0);

        //Verificando se sou ou não o líder com uma comparação simples
        if (smallestChild.equals(currentZnodeName)) {
            System.out.println("I am the leader");
            return;
        }

        System.out.println("I am not the leader, " + smallestChild + " is the leader");
    }

    public void connectToZookeeper() throws IOException {
    	// o terceiro parâmetro é um objeto do tipo watcher
    	// A API do zookeeper é baseada em evento e assíncrona.
    	//Para ser notificado sobre conexão e desconexão é necessário registrar
    	// um event handler e passar ao zookeeper como um objeto watcher
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    private void run() throws InterruptedException {
    	//Coloca a thread principal em um estado de espera;
    	// Precisa disso para podermos visualizar os outros prints como o de conexão estabelecida
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    private void close() throws InterruptedException {
        this.zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
    	//Recebendo um evento e verificando que tipo de evento é
        switch (event.getType()) {
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                } else {
                    synchronized (zooKeeper) {
                        System.out.println("Disconnected from Zookeeper event");
                        //o método notifyAll chama o método close que encerra a conexão
                        zooKeeper.notifyAll();
                    }
                }
        }
    }
}