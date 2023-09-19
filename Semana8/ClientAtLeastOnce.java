package Semana8_11;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientAtLeastOnce {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(socket.getInputStream());

        String message = "Mensagem do cliente (At Least Once)";
        int attempts = 0;
        boolean receivedConfirmation = false;

        while (attempts < 3 && !receivedConfirmation) {
            out.println(message);
            String confirmation = scanner.nextLine();
            System.out.println("Confirmação do servidor: " + confirmation);

            if (confirmation.equals("Recebido com sucesso")) {
                receivedConfirmation = true;
            } else {
                attempts++;
                System.out.println("Reenviando mensagem (Tentativa " + attempts + ")");
            }
        }

        if (!receivedConfirmation) {
            System.out.println("Falha na entrega após 3 tentativas.");
        }

        scanner.close();
        out.close();
        socket.close();
    }
}

