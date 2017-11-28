/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Camilo
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException{
        //Iniciando SerialComm
        try {
            SerialComm.connect("COM2");
        } catch (Exception ex) {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Iniciando Hilo de Entrada por Serial
        SerialReader reader = new SerialReader(SerialComm.getIn());
        new Thread(reader).start();
        
        System.out.println("Serial Comm Iniciado.");
        
//        //test de envio de informacion.
//        int y=65;
//        for(int i=0;i<5;i++){
//            Thread.sleep(2000);
//            SerialComm.sendTrama(i+y,y);
//        }
        
        //SerialComm.close();
    }
}
