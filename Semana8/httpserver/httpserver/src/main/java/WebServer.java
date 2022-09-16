

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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class WebServer {
	//1 - Temos 2 endpoints
	///task será o entrypoint para requests de tasks para o nosso servidor
	///status será o entrypoint para indicar o health (saúde) do nosso servidor
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";

    //porta em que o servidor irá escutar
    private final int port;
    private HttpServer server;

    //37 - criação do método main para realizar os testes
    public static void main(String[] args) {
    	// 38 - porta default
        int serverPort = 8080;
        //39 - caso a porta seja passada como argumento será parseado e atribuído o número da
        // porta recebido
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
       
        //40 - cria a instância do servidor web e inicializa
        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        //41 - print de sucesso e em qual porta o servidor está ouvindo
        System.out.println("Server is listening on port " + serverPort);
    }
    //2 - Método construtor em que será passada a porta para o servidor web
    public WebServer(int port) {
        this.port = port;
    }
    //3 - criar o servidor com o método create com o endereço e a porta em que o servidor
    // irá escutar. O backlog size é o número de requests que será permitido ao nosso servidor
    // em manter na fila se no momento não há threads suficientes ou poder computacional
    // para processá-los. Especificando zero ao backlog size será utilizado o default do sistema
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //4 - Definir os endpoints
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);

        //14 - Setando o handler do status context (statusContext) para o método startServer
        //para amarrar a implementação ao endpoint de status 
        statusContext.setHandler(this::handleStatusCheckRequest);
        
        //34 - Atribui o handler para o taskcontext. 
        taskContext.setHandler(this::handleTaskRequest);
        
        //35 - Alocação de threads para o servidor http para que ele consiga lidar com 
        // múltiplas requisições
        server.setExecutor(Executors.newFixedThreadPool(8));
        //36 - inicializa o servidor na porta
        server.start();
    }
  
    //15 - definindo o handler para o endpoint task que pega o httpexchange que representa
    // o status da transação entre cliente e servidor
    private void handleTaskRequest(HttpExchange exchange) throws IOException {
    	//16 - checando se o método é o post
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }
        //17 - Definindo uns headers customizados;
        // Pegando o primeiro valor colocado no header. Se o valor do header for true
        // iremos enviar uma mensagem para o cliente. Isso serve para testes e não irá realizar
        // uma computação muito longa
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }
        
        //18 - se não estamos no x-teste iremos testar o x-debug

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        // 19 - Pegando o tempo de computação
        long startTime = System.nanoTime();

        //20 - lendo as requisições do corpo da mensagem (requestBody)
        byte[] requestBytes = 	exchange.getRequestBody().readAllBytes();
        //21 - calculando os resultados...
        byte[] responseBytes = calculateResponse(requestBytes);

        //22 - final da operação. Pegamos o timestamp em nanosegundos
        long finishTime = System.nanoTime();

        
        //23 - Se estivermos em modo debug irá mostrar mensagem de quanto tempo levou a operação
        if (isDebugMode) {
            String debugMessage = String.format("Operation took %d ns", finishTime - startTime);
            //24 - coloca a informação de tempo como valor
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }
        //25 - Enviamos os headers de volta para o cliente
        sendResponse(responseBytes, exchange);
    }

    //26 - Implementando o cálculo. Pega por parâmetro os bytes enviados
    private byte[] calculateResponse(byte[] requestBytes) {
    	//27 converte o byte array para string
        String bodyString = new String(requestBytes);
        //28 quebra a string separando por vírgula para pegar a string de números
        String[] stringNumbers = bodyString.split(",");

        //29 - Armazena os valores em um biginteger
        BigInteger result = BigInteger.ONE;

        //30 - Itera sobre todos os números 
        for (String number : stringNumbers) {
        	//31 - converte em um biginteger
            BigInteger bigInteger = new BigInteger(number);
            //32 - multiplica de maneira cumulativa o resultado
            result = result.multiply(bigInteger);
        }
        //33 - cria a mensagem de resultado e converte em um array de bytes
        return String.format("Result of the multiplication is %s\n", result).getBytes();
    }
  //5 - Criação do método que irá cuidar/lidar com as requests que chegam ao servidor.
    //o argumento encapsula a transação htpp entre o servidor e o cliente
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    	//6 - verificação se o método é do tipo get assim como nós estamos esperando
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }
        //7 - mensagem de resposta como uma string

        String responseMessage = "Server is alive\n";
        //8 - envio da mensagem na responsebody
        sendResponse(responseMessage.getBytes(), exchange);
    }
    // 9 - Resposta pega um array de bytes para passar no corpo da mensagem de resposta e o 
    // httpexchange representando a transação
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    	//10 - setando o status code como 200 indicando que foi com sucesso,
    	//o segundo argumento será passado o tamanho do corpo da mensagem no header. Isso irá
    	// mostrar exatamente quanto bytes o cliente irá receber
        exchange.sendResponseHeaders(200, responseBytes.length);
        // 11 - Envio da mensagem via outputstream
        OutputStream outputStream = exchange.getResponseBody();
        // 12 - escreve a mensagem no fluxo
        outputStream.write(responseBytes);
        // 13 - Força a resposta para o usuário
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
