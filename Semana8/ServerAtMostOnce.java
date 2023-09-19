package Semana8_11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerAtMostOnce {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor At Most Once iniciado na porta 12345...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Scanner scanner = new Scanner(clientSocket.getInputStream());
            String message = scanner.nextLine();
            System.out.println("Mensagem recebida do cliente: " + message);

            scanner.close();
            clientSocket.close();
        }
    }
}

