package br.com.ifsp.jms;
import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

//Consumidor agora é durável
public class TesteConsumidorTopicoEstoque2 {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection(); 
      //criação da conexão
        connection.setClientID("estoque");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//Repare que a linha 30 mudou para o que está na linha 31 e o que está na linha 32 mudou para o que está na linha 33:
        //Destination topico = (Destination) context.lookup("loja");
        Topic topico = (Topic) context.lookup("loja");
        //MessageConsumer consumer = session.createConsumer(topico);
        // ebook is null OR ebook=false
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinaturaDoEstoque", "ebook=false",false);

        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {

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
