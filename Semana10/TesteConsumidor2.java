package br.com.ifsp.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidor2 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila );
		
		//Reparem que retiramos o método receive() da classe que fizemos anteriormente TesteConsumidor(veja linha 28 do git) e colocamos
		//o código abaixo
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				//Reparem que o método onMessage da interface MessageListener recebe uma Message
				//Existem mensagens mais específicas cada uma com seu objetivo
				//Utilização da subinterface TextMessage

				TextMessage textMessage = (TextMessage)message;
				
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
		
				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
