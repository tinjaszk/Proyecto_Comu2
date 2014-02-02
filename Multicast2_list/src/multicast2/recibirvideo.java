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
import java.net.Inet6Address;
import java.net.MulticastSocket;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import multicast2.ImagenPack;


public class recibirvideo extends Thread {
	
	int PuertoRecepcion;
	MulticastSocket Escuchador;
	
	boolean Correr;
	JLabel Reproductor;
	
	recibirvideo(int Puertorecepcion, String Grupo, JLabel repro){
		Correr=true;
		Inet6Address DirG;
		Reproductor = repro;
		PuertoRecepcion= Puertorecepcion;
		try {
			DirG = (Inet6Address) Inet6Address.getByName(Grupo);
			Escuchador = new MulticastSocket(PuertoRecepcion);
			Escuchador.joinGroup(DirG);
			Escuchador.setSendBufferSize(20000);
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
	public void run(){
		
		 byte [] cadena = new byte[20000] ; 
		 DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length);
		 while(Correr){
			 try {
				 Escuchador.receive(mensaje);
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
				 //System.out.println("MensajeMulticastC: "+ Mensaje + " "+ mensaje.getAddress().toString() );
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }		 
			 
		 }
                 
		 Escuchador.close();
		
		 
	}

}
