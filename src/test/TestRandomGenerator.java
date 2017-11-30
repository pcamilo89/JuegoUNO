/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Core;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class TestRandomGenerator {
    public static void main(String[] args){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestRandomGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(Utils.getRandomint(Core.getMax()+1));
        }
    }
}
