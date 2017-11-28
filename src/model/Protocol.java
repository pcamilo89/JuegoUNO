/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Camilo
 */
public class Protocol {
    
    public static void processTrama(Trama trama){
        //se convierte el segmento de control en string para subdividirlo
        String control= Utils.IntToBinary(trama.getControl());
        //System.out.println(control);
        
        String from = control.substring(0, 2);
        String to = control.substring(2, 4);
        String instruction = control.substring(4, 8);
        System.out.println(from+" "+to+" "+instruction);
    }
}
