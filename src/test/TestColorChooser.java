/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.swing.JFrame;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class TestColorChooser {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        int color = Utils.colorChooser("Elija Un Color", frame);
        System.out.println("Color: "+color);
    }
}
