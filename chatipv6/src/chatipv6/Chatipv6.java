
package chatipv6;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author miguelcardenas
 */
public class Chatipv6 {

    public static Socket client;

    Chatipv6() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final Ventana_cli cli = new Ventana_cli();
        cli.show();
        cli.area_chat.setEditable(false);

//fe80::9136:3d7a:8677:a19c wlan
        //fe80::61c0:7677:3729:7a6c lan
        client = null;
        try {
            client = new Socket("fe80::61c0:7677:3729:7a6c", 5001);
            cli.etiqueta_estado.setText("CONECTADO");
        } catch (IOException e) {
            System.out.println("Servidor no disponible");
            cli.etiqueta_estado.setText("Servidor no Disponible");
        }
        DataInputStream entrada = null;
        try {
            entrada = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            try {
                cli.area_chat.append(entrada.readLine() + "\n");
                //System.out.println(entrada.readLine() + "\n");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                break;
            }
        }
        


    }
}
