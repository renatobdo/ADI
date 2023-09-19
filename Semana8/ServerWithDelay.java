package Semana8_11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerWithDelay {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor com simulação de demora iniciado na porta 12345...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Scanner scanner = new Scanner(clientSocket.getInputStream());

            if (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                System.out.println("Servidor recebeu a mensagem do cliente: " + message);

                // Simula uma demora no servidor
                Thread.sleep(3000); // 3 segundos

                // Verifica o número de tentativas do cliente
                int attempt = Integer.parseInt(message.split(" ")[1]);
                System.out.println("tent = "+attempt);
                if (attempt == 3) {
                    // Responde apenas na terceira tentativa
                    String response = "Resposta do servidor na terceira tentativa";
                    System.out.println("Servidor responde na terceira tentativa.");
                    clientSocket.getOutputStream().write(response.getBytes());
                }
            }

            scanner.close();
            clientSocket.close();
        }
    }
}
