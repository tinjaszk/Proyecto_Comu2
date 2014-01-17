import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Rectangle;
import java.awt.Button;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Frame{

	private static final long serialVersionUID = 1L;
	public JTextArea jTextArea = null;
	private TextField texto = null;
	private Button button = null;
    public static Servidor forma;
    public static ServerSocket servidor;
    public static Socket clientes[];
    public static String conectados[];
    public static String salas[];
    public static int contador;
    public static int cont;


	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setText("");
			jTextArea.setEditable(true);
			jTextArea.setLocation(new Point(21, 56));
			jTextArea.setSize(new Dimension(405, 329));
		}
		return jTextArea;
	}

	
	private TextField getTexto() {
		if (texto == null) {
			texto = new TextField();
			texto.setBounds(new Rectangle(11, 400, 148, 23));
		}
		return texto;
	}

	
	private Button getButton() {
		if (button == null) {
			button = new Button();
			button.setBounds(new Rectangle(170, 397, 75, 23));
			button.setLabel("Enviar");
			button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//Click en el bot�n // TODO Auto-generated Event stub actionPerformed()
					DataOutputStream salida;
					for(int i=0;i<cont;i++)
					{
						if(clientes[i].isConnected())
						{try{
							salida = new DataOutputStream(clientes[i].getOutputStream());
							salida.writeBytes("Servidor dice: "+texto.getText()+"\n");							
						    }
						catch(Exception ex)
						 {
							
						 }
						}
					}
				}
			});		
	
		}
		return button;
	}

	
	public static void main(String[] args)throws Exception {
		
        forma=new Servidor();
        clientes=new Socket[20];
        conectados=new String [100];
        salas=new String [100];
        servidor=null;
        Hilo h;
          
        try{
            servidor=new ServerSocket(5001);
            }
        catch(Exception e){
             System.out.println(e.getMessage());
            }
        System.out.println("Servidor Listo...");
     while(true){
    	 clientes[cont]=servidor.accept(); // vector de sockets
    	 
    	 //System.out.println(clientes[cont].getRemoteSocketAddress()+" Conectado...");
    	 //forma.jTextArea.append(clientes[cont].getRemoteSocketAddress()+" Conectado...\n");
    	 
    	 h=new Hilo(clientes[cont],forma);
    	 h.start();
    	 cont++;
       }
     
	}

	/**
	 * This is the default constructor
	 */
	public Servidor() {
		super();
		initialize();
		cont=0;
                contador=0;
                
	}

	
	private void initialize() {
		this.setSize(492, 461);
		this.setTitle("Socket Servidor");
                this.setLayout(null);
		this.add(getJTextArea());		
		this.add(getTexto(), null);
		this.add(getButton(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				// TODO Auto-generated Event stub windowClosing()
				System.exit(0);
			}
		});
	
		
		this.show();
		
	}

}  //  @jve:decl-index=0:visual-constraint="14,20"
class Hilo extends Thread
{
	Socket cliente;
	Servidor formulario;
	DataInputStream entrada;
public Hilo(Socket cli,Servidor forma)throws Exception
{  
	cliente=cli;
	formulario=forma;
	entrada=new DataInputStream(cliente.getInputStream());
}	
	public void run(){
		// TODO Auto-generated method stub
		String a=null;
                boolean usado=false;
		boolean error=false;
                DataOutputStream salida;
                
   while(true){
	   try{
	   a=entrada.readLine();
           String[] pedazos=a.split(":");
           if("Conectado".equals(pedazos[0])){
               Servidor.conectados[Servidor.cont]=pedazos[1];
               Servidor.salas[Servidor.cont]=pedazos[2];
               System.out.println("Solicitud");
               //System.out.println("Usuario: "+Servidor.conectados[Servidor.cont]);
               //System.out.println("Sala: "+Servidor.salas[Servidor.cont]);
           }
           formulario.jTextArea.append(a+"\n");
           for (int i = 0; i < Servidor.cont; i++) {
               if((Servidor.conectados[i]!=null)&&(Servidor.salas!=null)){
               System.out.println("Usuario: "+Servidor.conectados[i]);
               System.out.println("Sala: "+Servidor.salas[i]);
               System.out.println("Pedazos[2]: "+Servidor.salas[i]);
               System.out.println("-------------------------------------");
                if ((Servidor.salas[i]==pedazos[2])) {
                    System.out.println("salas: "+Servidor.salas[i]+" pedazos: "+pedazos[2]);
                    try {
                        salida = new DataOutputStream(Servidor.clientes[i].getOutputStream());
                        
                        salida.writeUTF(pedazos[0]+":"+pedazos[1]+"\n");
                        
                   } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
           }
	   }
	   catch(IOException e)
	   {
		   System.out.println(e.getMessage());
		   error=true;
	   }
	   if("salir".equals(a)||!cliente.isConnected()||error)
	   {
		   try{
		   cliente.close();
	       }
	   catch(IOException e)
	    {		   
	    }		 
	    break;
	   }	   
		   //formulario.jTextArea.append("Cliente: "+cliente.getRemoteSocketAddress()+"Mensaje:"+a+"\n");
               
           
   }
	//formulario.jTextArea.append("Cliente: "+cliente.getRemoteSocketAddress()+" Desconectado");
        String[] pedazos=a.split(":");
        
        formulario.jTextArea.append("Desconectado: "+pedazos[0]+"\n");
   for (int i = 0; i < Servidor.cont; i++) {
                if (Servidor.clientes[i].isConnected()) {
                    try {
                        salida = new DataOutputStream(Servidor.clientes[i].getOutputStream());
                        salida.writeUTF("Desconectado: "+pedazos[0]+"\n");
                   } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }
	try{
	cliente.close();
	}
	catch(Exception e){		
	
   }	
 }	
}