/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hilo de escucha al canal de entrada RX del puerto serial
 * @author Camilo
 */
public class SerialReader implements Runnable{
    //Canal de entrada RX
    private InputStream in;
    
    /**
     * Constructor del hilo.
     * @param in Canal de entrada del puerto serial.
     */
    public SerialReader ( InputStream in )
    {
        this.in = in;
    }

    /**
     * Metodo que se ejecuta al iniciar el hilo.
     */
    public void run ()
    {
        byte[] buffer = new byte[1];
        int len = -1;
        String msg;
        char[] arr;
        int c;
        Trama trama = new Trama();
        try
        {
            //ciclo de lectura del buffer de entrada
            while ( ( len = this.in.read(buffer)) > -1 )
            {

                msg = new String(buffer,0,len);
                arr = msg.toCharArray();

                //Segmento donde se maneja cuando se recibe cualquier caracter.
                if(arr.length > 0){
                    
                    c = (int) arr[0];
                    //Utils.printMSG(c);

                    /* Se llena la 'trama' de tamaño 4 para comprobar mensaje valido
                     * y caso de ser valido se procesa.
                    */
                    trama.fillTrama(c);
                    if(trama.checkTrama()){
                        //trama.printTrama();
                        Protocol.processTrama(trama);
                    }
                }

            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }            
    }
}
