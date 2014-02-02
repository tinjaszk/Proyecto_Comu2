/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multicast2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
     
     
  

   
    
    public static void main(String[] args) throws UnknownHostException, IOException {
            
          
          String mac="20-6A-8A-54-EF-A5"; 
          String nick ="Tinjaszk";
          String MCAST_ADDR = "ff02::1";
          int puerto = 20001; 
          int bandera = 1;
          String msg ;
          
          ArrayList<String> lcontact = new ArrayList<>();
          
          lcontact.add(nick);
          
          Multicast2 obj = new Multicast2();
          
          obj.vent.show();
         

            msg = "CONECT_"+mac+"_"+nick+"_";
        
            InetAddress group = InetAddress.getByName(MCAST_ADDR);
            MulticastSocket s = new MulticastSocket(puerto);
            s.joinGroup(group);
            DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, puerto);
            s.send(hi);
            
            //obj.vent.imgc1.setIcon(obj.image);
            
            
            
        while (true) {
           
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);            
            s.receive(recv);
            String men = new String(buf);
            
              String[] vec_men = men.split("_");
              
              if (vec_men[0].equals("CONECT"))
              {
                  
                  
                  if ( !lcontact.contains(vec_men[2])  )
                  {
                      
                      lcontact.add(vec_men[2]);
                      
                      if(bandera==1)
                      {
                          obj.vent.cont1.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(5003,MCAST_ADDR ,obj.vent.imgc1);
                      }
                      if(bandera==2)
                      {
                          obj.vent.cont2.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(5003,MCAST_ADDR ,obj.vent.imgc2);
                      }
                      if(bandera==3)
                      {
                          obj.vent.cont3.setText(vec_men[2]);
                          recibirvideo recibe=new recibirvideo(5003,MCAST_ADDR ,obj.vent.imgc3);
                      }
                      
                      bandera++;
                      
                      //msg = "CONECT_"+mac+"_"+nick+"_"+vec_men[2];
                      
                      s.send(hi);
                      
                      ImagenPack imp = new  ImagenPack(obj.image.getImage());
                      
                      VSender vs= new VSender(5003, MCAST_ADDR);
                      vs.Send(imp, MCAST_ADDR);
                      
                   
                  }

                  System.out.println("el mensaje es: "+men);
              
              }
            
            
            
        }//While TRUE
    }


    
}
