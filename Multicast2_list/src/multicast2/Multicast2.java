/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multicast2;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.NoPlayerException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author HenryGBC
 */
public class Multicast2  {
    
     //public BufferedImage img = null ;
     public contactos vent = new contactos();
     
     public JLabel img = new JLabel("");
     public ImageIcon image = new ImageIcon("hector.jpg");
     
     public  int puerto = 25000; 
     
     public  InetAddress group ;
     public  MulticastSocket s ;
     public DatagramPacket hi ;
     public DatagramPacket recv;
     
     
     public ArrayList<client> lclient=new ArrayList<client>();
     
     
     ///fe80:0:0:0:4074:3017:2772:87c2%16
     public String midireccion="/fe80:0:0:0:4074:3017:2772:87c2%16";

     public boolean buscar(String direcc){
         
         Iterator<client> nombreIterator = lclient.iterator();
        while(nombreIterator.hasNext()){
            client elemento = nombreIterator.next();
            if(elemento.direccionIP.equals(direcc))
                return true;
        }//fin del while
         return false;
     }//find del metodo buscar
     
     public void eventos ()
     {
         vent.cont1.addMouseListener(new MouseAdapter()  
            {  
                @Override
                public void mouseClicked(MouseEvent e)  
                {  
                   chat ch = null;
                    try {
                        ch = new chat();
                    } catch (IOException ex) {
                        Logger.getLogger(Multicast2.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoPlayerException ex) {
                        Logger.getLogger(Multicast2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   ch.setVisible(true);
                   ch.show();
                }  
            }); 
     
     }

   
    
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
            
          
          String mac="20-6A-8A-54-EF-A5"; 
          String nick ="Tinjafin";
          String MCAST_ADDR = "ff02::1";
          
          int bandera = 1;
          String msg ;
          
          ArrayList<String> lcontact = new ArrayList<>();
          Multicast2 obj = new Multicast2();
          client yo=new client(nick,obj.midireccion, -1);
          obj.lclient.add(yo);
          
          lcontact.add(nick);
       
          obj.vent.show();
         

            msg = "CONECT_"+mac+"_"+nick+"_";
        
            obj.group = InetAddress.getByName(MCAST_ADDR);
            obj.s = new MulticastSocket(obj.puerto);
            obj.s.joinGroup(obj.group);
            obj.hi = new DatagramPacket(msg.getBytes(), msg.length(), obj.group, obj.puerto);
            obj.s.send(obj.hi);
            
            //obj.vent.imgc1.setIcon(obj.image);
            
        obj.eventos();
        
        int auxhilo=0;
        String dir = null;
        
        while (true) {
           
            byte[] buf = new byte[1000];
            obj.recv = new DatagramPacket(buf, buf.length);            
            
            do{
                
                obj.s.receive(obj.recv);
                 dir = obj.recv.getAddress().toString();
                
            }while( obj.recv.getAddress().toString().equals(obj.midireccion));
            
            String men = new String(buf);
            
              String[] vec_men = men.split("_");
              
              if (vec_men[0].equals("CONECT") && !lcontact.contains(vec_men[2]))
              {
                  
                      lcontact.add(vec_men[2]);
             
                      
                      if(bandera==1)
                      {
                          obj.vent.cont1.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(25001,MCAST_ADDR ,obj.vent.imgc1,yo.direccionIP);
                          auxhilo=1;
                      }
                      if(bandera==2)
                      {
                          obj.vent.cont2.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(25001,MCAST_ADDR ,obj.vent.imgc2,yo.direccionIP);
                          auxhilo=1;
                      }
                      if(bandera==3)
                      {
                          obj.vent.cont3.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(25001,MCAST_ADDR ,obj.vent.imgc3,yo.direccionIP);
                          auxhilo=1;
                      }
                      
                      
                      ImagenPack imp = new  ImagenPack(obj.image.getImage());
                      
                      VSender vs= new VSender(25001, MCAST_ADDR);
                      vs.Send(imp, MCAST_ADDR);
                      vs.matar();
                      
                      client aux=new client(vec_men[2],obj.recv.getAddress().toString(),bandera);
                      obj.lclient.add(aux);
                      
                      bandera++;
                    
                      obj.s.send(obj.hi);

                      System.out.println("el mensaje es: "+men);
                  

                  
              
              }
            
            System.out.println("el mensaje es while: "+men);
            
        }//While TRUE
    }


    
}
