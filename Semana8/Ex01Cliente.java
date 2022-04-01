/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semana8_8;
//C:\Users\renat\OneDrive\Área de Trabalho\Renato\DisciplinasProfessorIFSPGuarulhos\ADI\backupEclipse\Treinamentos\IFSP\src\semana2socketsRnp
import java.awt.HeadlessException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Paulo Henrique Cayres
 */
public class Ex01Cliente {

    
    public static void main(String[] args) {

        try{
            // Abrir a conex�o para cliente, na porta 1234
            Socket cliente = new Socket("localhost",1234);
            
            //Cria um canal para enviar dados
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
            out.writeInt(Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Digite o primeiro n�mero: ")));
            out.writeInt(Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Digite o segundo n�mero: ")));
            
            //http://tutorials.jenkov.com/java-io/dataoutputstream.html
            //out.writeInt(2);
            //out.writeInt(8);
            
            //Cria um canal para receber dados
            DataInputStream in = new DataInputStream(cliente.getInputStream());
            javax.swing.JOptionPane.showMessageDialog(null, "O somatório é "+in.readInt(), "Resultado", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            //Fechar conexões
            cliente.close();
            in.close();
            out.close();
                  
        }catch(IOException | HeadlessException | NumberFormatException e){
            System.out.println(e.toString());
        }
        
    }
    
}
