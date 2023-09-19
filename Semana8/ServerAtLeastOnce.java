package Semana8_11;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerAtLeastOnce {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor At Least Once iniciado na porta 12345...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Scanner scanner = new Scanner(clientSocket.getInputStream());
            String message = scanner.nextLine();
            System.out.println("Mensagem recebida do cliente: " + message);

            
            // Envia confirmação de recebimento para o cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Recebido com sucesso");

            scanner.close();
            out.close();
            clientSocket.close();
        }
    }
}

