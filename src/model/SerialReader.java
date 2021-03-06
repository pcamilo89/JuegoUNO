/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hilo de escucha al canal de entrada RX del puerto serial
 * @author Camilo
 */
public class SerialReader implements Runnable{
    //Canal de entrada RX
    private InputStream in;
    private boolean live = true;
    /**
     * Constructor del hilo.
     * @param in Canal de entrada del puerto serial.
     */
    public SerialReader ( InputStream in )
    {
        this.in = in;
    }

    /**
     * Metodo para consultar si el hilo sige activo
     * @return boolean true or false
     */
    public boolean isLive() {
        return live;
    }

    /**
     * Metodo para setear si el hilo seguira con vida
     * @param live boolean true or false
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * Metodo que se ejecuta al iniciar el hilo.
     */
    @Override
    public void run ()
    {
        try {
            byte[] buffer = new byte[1];
            int len = -1;
            String msg;
            int c ;
            Trama trama = new Trama();
            
            //Ciclo de lectura del buffer de entrada
            while ( ( len = this.in.read(buffer)) > -1 && live)
            {
                try {
                    //PARA TESTEAR
                    Thread.sleep(Utils.SLEEP_TIME_SHORT);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
                }
                                
                msg = new String(buffer,0,len);
                
                //Segmento donde se maneja cuando se recibe cualquier caracter
                if(msg.length() > 0){
                    
                    c = Utils.unsignedToBytes(buffer[0]);

                    /* Se llena la 'trama' de tamaño 4 para comprobar mensaje valido
                    * y caso de ser valido se procesa.
                    */
                    //Utils.printMSG(c);
                    trama.fillTrama(c);
                    if(trama.checkTrama()){
                        //trama.printTrama();
                        Protocol.processTrama(trama);
                    }
                    
                }
                System.out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(SerialReader.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
    }
}
