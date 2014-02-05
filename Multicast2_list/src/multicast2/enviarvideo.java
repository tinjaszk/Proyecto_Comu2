/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast2;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.MulticastSocket;
import java.net.URL;
import java.net.UnknownHostException;
import javax.media.Buffer;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.media.control.FrameGrabbingControl;
import javax.media.control.FramePositioningControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class enviarvideo  implements ControllerListener {
	
	Buffer buf = null ;
	Image img = null;
	BufferToImage btoi = null;
	Player player = null ;
	JLabel imagen;
        JPanel panel;
	boolean reproduciendo;
	boolean started;
	boolean muerto;
	VSender Difusor;
	
	public enviarvideo (Player pl, JPanel image , String Grupo,int puert) throws IOException, NoPlayerException{
		 super ( ) ;
		panel=image; 
                //imagen=image;
		 started=false;
		 muerto=false;
		 Difusor = new VSender(puert, Grupo);
                 player=pl;
		 Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );    
		 	//player = new MediaPlayer();
		 	//player.setMediaLocation(a.toString());
		
			// player = Manager.createPlayer( new MediaLocator ( a ) ) ;
		 
		     player.addControllerListener ( this ) ;
		     //System.out.println ( "Adquicion de recursos y medios de informaciÃ³n en curso." ) ;
		     //System.out.println ( "Por favor espere..." ) ;
		     //player.realize ( ) ;
	}
	
	public void matar(){
		muerto=true;
		imagen.setIcon(null);
		Difusor.matar();
	}
	
	public void parar(){
		reproduciendo = false;
	}
	
	public void seguir(){
		reproduciendo = true;
		if(started==false){
			player.realize();
			started=true;
		}
	}
	
	
        @Override
	public void controllerUpdate ( ControllerEvent ce ){
		if ( ce instanceof RealizeCompleteEvent ){
			     
                    int NomImage = 1 ;
			     @SuppressWarnings("unused")
				
			     FrameGrabbingControl fgc ;
  
				     while ( true){
				    	 
                                         try {
				        	 if (NomImage==1) Thread.sleep(3000);
				         } catch (InterruptedException e) {
				        	 e.printStackTrace();
				         }
				    	 if(muerto){
				    		 break;
				    	 }
                                         
				    	 if(reproduciendo){
				    		 
						         
						         fgc = ( FrameGrabbingControl ) player. getControl ( "javax.media.control.FrameGrabbingControl" ) ;
						         buf = fgc. grabFrame ( ) ;
						         btoi = new BufferToImage ( ( VideoFormat ) buf.getFormat ( ) ) ;
						         NomImage = NomImage + 1 ;
						         Image img2 = btoi.createImage ( buf ) ;
                                                         
                                                     
						         ImagenPack pack = new ImagenPack(img2);
						         try {
									Difusor.Send(pack, Difusor.Direccion);//Redundancia con la Direccion
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						         
				    		 }
				     }
			     }
		   }
	 
	
}

class VSender2{
	
	MulticastSocket Avisador;
	int PuertoEnvio;
	boolean Abierto;
	String Direccion;
	
	public VSender2(int Puertoenvio, String Grupo){
		Direccion = Grupo;
		Abierto=true;
		PuertoEnvio=Puertoenvio;
		
		try {
			Avisador =  new MulticastSocket();
			System.out.println("TamaÃ±o Maximo:"+Avisador.getSendBufferSize());
			//start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al Crear Avisador Multicast");
		}
		
	}
	
	public void matar(){
		Avisador.close();
	}
	
	
	public void Send(ImagenPack Imagen, String Grupo) throws IOException{
	
	//Reconvertir
	//ByteArrayInputStream bs= new ByteArrayInputStream(bytes); // bytes es el byte[]
	//ObjectInputStream is = new ObjectInputStream(bs);
	//ClaseSerializable unObjetoSerializable = (ClaseSerializable)is.readObject();
	//is.close();
		
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream (bs);
		os.writeObject(Imagen);  // this es de tipo DatoUdp
		os.close();
		byte[] cadena =  bs.toByteArray();
		Inet6Address DirG;
		try {
			DirG = (Inet6Address) Inet6Address.getByName(Grupo);
			DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length, DirG, PuertoEnvio);
			
			System.out.println(mensaje.getLength());
			Avisador.send(mensaje);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al Construir Paquete Multicast.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al Enviar Paquete Multicast.");
		}
		
	}
	
	
}
