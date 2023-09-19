package Semana8_11;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWithDelay {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(socket.getInputStream());

        for (int attempt = 1; attempt <= 3; attempt++) {
            String message = "Tentativa " + attempt;
            System.out.println("Enviando mensagem para o servidor: " + message);
            out.println(message);
            out.print(message);
            
            // Aguarda a resposta do servidor
            if (scanner.hasNextLine()) {
                String response = scanner.nextLine();
                System.out.println("Resposta do servidor: " + response);

                if (response.contains("Resposta do servidor na terceira tentativa")) {
                	System.out.println("Resposta do servidor na terceira tentativa: " + response);
                    break; // Se receber a resposta desejada, sai do loop
                }
            }
        }

        scanner.close();
        out.close();
        socket.close();
    }
}
