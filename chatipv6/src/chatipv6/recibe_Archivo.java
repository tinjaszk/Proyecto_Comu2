/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatipv6;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author miguelcardenas
 */
public class recibe_Archivo {
    public static void main(String[] args) {
        Socket connection;
ServerSocket server;
         DataOutputStream output;
         BufferedInputStream bis;
         BufferedOutputStream bos;

         byte[] receivedData;
         int in;
         String file;
         
         
         
         try{
             server = new ServerSocket( 3000 );
             while ( true ) {
                 connection = server.accept();
                 System.out.println("entro al ciclo");
                 receivedData = new byte[1024];
                 bis = new BufferedInputStream(connection.getInputStream());
                 try (DataInputStream dis = new  DataInputStream(connection.getInputStream())) {
                     file = dis.readUTF();
                     file = file.substring(file.indexOf('/')+1,file.length());

                     bos = new BufferedOutputStream(new FileOutputStream("/Users/miguelcardenas/Desktop/"+file));
                     System.out.println("dio memoria");
                     while ((in = bis.read(receivedData)) != -1){
                         bos.write(receivedData,0,in);
                         System.out.println("recibe datos");
                     }
                     bos.close();
                     
                 }
             }

         }catch (Exception e ) {
             System.err.println(e);
         }
    }
}
