/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multicast2;

/**
 *
 * @author SredxNY
 */
public class client {
    
    public String nick,direccionIP;
    public int posicion;
    public boolean isFirstCon;
    
    public client(){
        nick="";
        direccionIP="";
        posicion=0;
        isFirstCon=true;
    }//fin del vacio
    
    public client(String nick_n,String dir_n,int pos_n){
        nick=nick_n;
        direccionIP=dir_n;
        posicion=pos_n;
    }//fin del constructor sobrecargado
}
