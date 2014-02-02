/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast2;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImagenPack implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	byte []F;
	
	public ImagenPack (Image imagen){
		File ImagenF;
              
		BufferedImage buf = new BufferedImage(imagen.getWidth(null),imagen.getHeight(null),BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = buf.createGraphics();
                g2.drawImage(imagen, 0, 0,null);
                g2.dispose();
                
		try {
			ImageIO.write(buf, "jpg", new File("buffer.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImagenF = new File("buffer.jpg");
		try {
			F = Files.readAllBytes(Paths.get("buffer.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
