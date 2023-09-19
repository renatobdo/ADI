package Semana8_11;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAtMostOnce {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Enviando mensagem uma vez
        out.println("Mensagem do cliente (At Most Once)");

        out.close();
        socket.close();
    }
}

