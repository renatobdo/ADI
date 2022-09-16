

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
	///task ser� o entrypoint para requests de tasks para o nosso servidor
	///status ser� o entrypoint para indicar o health (sa�de) do nosso servidor
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";

    //porta em que o servidor ir� escutar
    private final int port;
    private HttpServer server;

    //37 - cria��o do m�todo main para realizar os testes
    public static void main(String[] args) {
    	// 38 - porta default
        int serverPort = 8080;
        //39 - caso a porta seja passada como argumento ser� parseado e atribu�do o n�mero da
        // porta recebido
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
       
        //40 - cria a inst�ncia do servidor web e inicializa
        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        //41 - print de sucesso e em qual porta o servidor est� ouvindo
        System.out.println("Server is listening on port " + serverPort);
    }
    //2 - M�todo construtor em que ser� passada a porta para o servidor web
    public WebServer(int port) {
        this.port = port;
    }
    //3 - criar o servidor com o m�todo create com o endere�o e a porta em que o servidor
    // ir� escutar. O backlog size � o n�mero de requests que ser� permitido ao nosso servidor
    // em manter na fila se no momento n�o h� threads suficientes ou poder computacional
    // para process�-los. Especificando zero ao backlog size ser� utilizado o default do sistema
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

        //14 - Setando o handler do status context (statusContext) para o m�todo startServer
        //para amarrar a implementa��o ao endpoint de status 
        statusContext.setHandler(this::handleStatusCheckRequest);
        
        //34 - Atribui o handler para o taskcontext. 
        taskContext.setHandler(this::handleTaskRequest);
        
        //35 - Aloca��o de threads para o servidor http para que ele consiga lidar com 
        // m�ltiplas requisi��es
        server.setExecutor(Executors.newFixedThreadPool(8));
        //36 - inicializa o servidor na porta
        server.start();
    }
  
    //15 - definindo o handler para o endpoint task que pega o httpexchange que representa
    // o status da transa��o entre cliente e servidor
    private void handleTaskRequest(HttpExchange exchange) throws IOException {
    	//16 - checando se o m�todo � o post
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }
        //17 - Definindo uns headers customizados;
        // Pegando o primeiro valor colocado no header. Se o valor do header for true
        // iremos enviar uma mensagem para o cliente. Isso serve para testes e n�o ir� realizar
        // uma computa��o muito longa
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }
        
        //18 - se n�o estamos no x-teste iremos testar o x-debug

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        // 19 - Pegando o tempo de computa��o
        long startTime = System.nanoTime();

        //20 - lendo as requisi��es do corpo da mensagem (requestBody)
        byte[] requestBytes = 	exchange.getRequestBody().readAllBytes();
        //21 - calculando os resultados...
        byte[] responseBytes = calculateResponse(requestBytes);

        //22 - final da opera��o. Pegamos o timestamp em nanosegundos
        long finishTime = System.nanoTime();

        
        //23 - Se estivermos em modo debug ir� mostrar mensagem de quanto tempo levou a opera��o
        if (isDebugMode) {
            String debugMessage = String.format("Operation took %d ns", finishTime - startTime);
            //24 - coloca a informa��o de tempo como valor
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }
        //25 - Enviamos os headers de volta para o cliente
        sendResponse(responseBytes, exchange);
    }

    //26 - Implementando o c�lculo. Pega por par�metro os bytes enviados
    private byte[] calculateResponse(byte[] requestBytes) {
    	//27 converte o byte array para string
        String bodyString = new String(requestBytes);
        //28 quebra a string separando por v�rgula para pegar a string de n�meros
        String[] stringNumbers = bodyString.split(",");

        //29 - Armazena os valores em um biginteger
        BigInteger result = BigInteger.ONE;

        //30 - Itera sobre todos os n�meros 
        for (String number : stringNumbers) {
        	//31 - converte em um biginteger
            BigInteger bigInteger = new BigInteger(number);
            //32 - multiplica de maneira cumulativa o resultado
            result = result.multiply(bigInteger);
        }
        //33 - cria a mensagem de resultado e converte em um array de bytes
        return String.format("Result of the multiplication is %s\n", result).getBytes();
    }
  //5 - Cria��o do m�todo que ir� cuidar/lidar com as requests que chegam ao servidor.
    //o argumento encapsula a transa��o htpp entre o servidor e o cliente
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
    	//6 - verifica��o se o m�todo � do tipo get assim como n�s estamos esperando
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
    // httpexchange representando a transa��o
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
    	//10 - setando o status code como 200 indicando que foi com sucesso,
    	//o segundo argumento ser� passado o tamanho do corpo da mensagem no header. Isso ir�
    	// mostrar exatamente quanto bytes o cliente ir� receber
        exchange.sendResponseHeaders(200, responseBytes.length);
        // 11 - Envio da mensagem via outputstream
        OutputStream outputStream = exchange.getResponseBody();
        // 12 - escreve a mensagem no fluxo
        outputStream.write(responseBytes);
        // 13 - For�a a resposta para o usu�rio
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
