/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semana8_8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Paulo Henrique Cayres
 */
public class Ex01Servidor {

	public static void main(String argv[]) {
        try {
        	
        	//1 - Definir o serverSocket (abrir porta de conexão)
            ServerSocket socketRecepcao = new ServerSocket(1234);
            System.out.println("Servidor esperando conexão na porta 1234");

          //2 - Aguardar solicitação de conexão de cliente
            Socket socketConexao = socketRecepcao.accept();
            System.out.println("Socket Conexão: "+socketConexao);
            //Mostrar endereço IP do cliente conectado
            System.out.println("Cliente " + socketConexao.getInetAddress().getHostAddress() + " conectado");

            //Cria um canal para receber dados
            //Veja a documentação http://docs.oracle.com/javase/8/docs/api/index.html
          //3 - Definir stream de entrada de dados no servidor
            DataInputStream in = new DataInputStream(socketConexao.getInputStream());
            
            //4 - passa os valores recebidos para a função que irá realizar o cálculo
            int somatorio = FuncoesServidor.calcSomatorio(in.readInt(), in.readInt());
          
            //5 - Definir stream de saída de dados do servidor
            DataOutputStream out = new DataOutputStream(socketConexao.getOutputStream());
            out.writeInt(somatorio); //Enviar resultado da operação para cliente

            //6 - Fechar streams de entrada e saída de dados
            in.close();
            out.close();
            
            //7 - Fechar sockets de conexão e comunicação
            socketConexao.close();
            socketRecepcao.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
