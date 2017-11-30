/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SerialComm;
import model.SerialReader;
import model.Trama;

/**
 *
 * @author Camilo
 */
public class TestEnvioTrama {
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
        Trama trama = new Trama(128, 240);
        SerialComm.sendTrama(trama);
        reader.setLive(false);
        SerialComm.close();
    }
}
