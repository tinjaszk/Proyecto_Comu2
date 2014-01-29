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

/**
 *
 * @author HenryGBC
 */
public class Multicast2 {

      public static void main(String[] args) throws UnknownHostException, IOException {
        String msg = "SEXO POR CAM";
            InetAddress group = InetAddress.getByName("ff02::1:4");
            MulticastSocket s = new MulticastSocket(5001);
            s.joinGroup(group);
            DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, 5001);
            s.send(hi);
        while (true) {
            
            // get their responses!
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);            
            s.receive(recv);
            String men = new String(buf);
            System.out.println("el mensaje es: "+men);
            // OK, I'm done talking - leave the group...
          
            
        }
    }
}
