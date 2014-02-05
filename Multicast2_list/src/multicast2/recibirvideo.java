/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast2;


import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class recibirvideo extends Thread {
	
	int PuertoRecepcion;
	MulticastSocket Escuchador;
	public String midireccion; 
	boolean Correr;
	JLabel Reproductor;
	
	recibirvideo(int Puertorecepcion, String Grupo, JLabel repro, String dir){
		Correr=true;
		InetAddress DirG;
		Reproductor = repro;
                midireccion=dir;
		PuertoRecepcion= Puertorecepcion;
		try {
			DirG =   InetAddress.getByName(Grupo);
			Escuchador = new MulticastSocket(PuertoRecepcion);
			Escuchador.joinGroup(DirG);
			Escuchador.setSendBufferSize(100000);
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al Crear Escuchador de Video Multicast.");
		}
	}
	
	public void Stop(){
		
		Correr=false;
		 
	}
	
	@SuppressWarnings("deprecation")
        @Override
	public void run(){
		
		 byte [] cadena = new byte[1000000] ; 
		 DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length);
		 while(Correr){
			 try {
				 Escuchador.receive(mensaje);
                                 
                                                                     
                                 System.out.println(" DIRECCION: "+mensaje.getAddress().toString()+" MENSAJE: "+mensaje.toString());

                                 
                                 if ( !mensaje.getAddress().toString().equals(midireccion) )
                                 {
                                     
                                     System.out.println(" IF DIRECCION: "+mensaje.getAddress().toString()+" MENSAJE: "+mensaje.toString());
                                           
                                     ByteArrayInputStream bs= new ByteArrayInputStream(cadena); // bytes es el byte[]
                                            ObjectInputStream is = new ObjectInputStream(bs);
                                            
                                            try {
                                                   ImagenPack pack = (ImagenPack)is.readObject();
                                                   OutputStream out = new FileOutputStream("cache.jpg");
                                                   out.write(pack.F);
                                                   out.close();
                                                   ImageIcon mmm = new ImageIcon(pack.F);
                                                   Reproductor.setIcon( mmm);
                                           } catch (ClassNotFoundException e) {
                                                   // TODO Auto-generated catch block
                                                   e.printStackTrace();
                                           }
                                            is.close();
                                 }//getaddress
                                 
				 //System.out.println("MensajeMulticastC: "+ Mensaje + " "+ mensaje.getAddress().toString() );
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }		 
			 
		 }
                 
		 Escuchador.close();
		
		 
	}

}
