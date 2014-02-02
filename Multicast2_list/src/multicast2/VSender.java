package multicast2;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;



class VSender implements Serializable{
	
	MulticastSocket Avisador;
	int PuertoEnvio;
	boolean Abierto;
	String Direccion;
	
	public VSender(int Puertoenvio, String Grupo){
		Direccion = Grupo;
		Abierto=true;
		PuertoEnvio=Puertoenvio;
		
		try {
			Avisador =  new MulticastSocket();
			System.out.println("Tamaño Maximo:"+Avisador.getSendBufferSize());
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
                
                
                
//                for(int i=0; i<cadena.length ; i++)
//                {
//                    System.out.println(""+cadena[i]);
//                
//                }
                
		InetAddress DirG;
		try {
			DirG = (InetAddress) InetAddress.getByName(Grupo);
			DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length, DirG, PuertoEnvio);
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