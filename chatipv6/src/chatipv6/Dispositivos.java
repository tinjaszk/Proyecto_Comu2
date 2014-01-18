/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatipv6;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.format.RGBFormat;
import javax.media.format.YUVFormat;
import javax.swing.JPanel;

/**
 *
 * @author Miguel Cardenas
 */
public class Dispositivos {

    private Ventana_cli padre;
    private Player player;

    public Dispositivos(Ventana_cli padre)
    {
        this.padre=padre;
    }
    
    public void MuestraWebCam(JPanel panelCam,String dispositivo)
    {
        
        if(player != null)
            return;
        CaptureDeviceInfo dev = CaptureDeviceManager.getDevice(dispositivo);
        //obtengo el locator del dispositivo
        MediaLocator loc = dev.getLocator();
            try {
                player = Manager.createRealizedPlayer(loc);
            } catch (IOException ex) {
                Logger.getLogger(Chatipv6.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoPlayerException ex) {
                Logger.getLogger(Chatipv6.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CannotRealizeException ex) {
                Logger.getLogger(Chatipv6.class.getName()).log(Level.SEVERE, null, ex);
            }
        player.start();

    /*    try {
            // esto lo saqué del foro jmf de Sun, hay que "parar un poco la aplicacion"
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        Component comp;

        if ((comp = player.getVisualComponent())!= null)
        {
          // mostramos visualmente el reproductor          
          panelCam.add(comp,BorderLayout.CENTER);
          panelCam.updateUI();
        //  padre.pack();
          
        }
        
    }
     public void getCerrarCam()    {
      if(player != null){
            player.close();
            player.deallocate();
            //timer.stop();
            padre.getjPwebcam().removeAll();
            player=null;
      }
    }
    
    
}
