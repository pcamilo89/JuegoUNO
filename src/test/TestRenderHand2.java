/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import view.HandPanel;

/**
 *
 * @author Camilo
 */
public class TestRenderHand2 {
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        HandPanel panel = new HandPanel(0);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);


        //panel.setBackground(Color.WHITE);
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        
        frame.add(panel2);
        
        
        panel2.add(panel);
        frame.setVisible(true);
        panel.setBounds(0, 0, panel2.getWidth(), panel2.getHeight());
        
      
        int i = 1;
        while(true){
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestRenderHand2.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.setBounds(0, 0, panel2.getWidth(), panel2.getHeight());
            panel.setCardNumber(i);
            panel.cardDraw();
            
            i++;
            if(i>20){
                i=1;
            }
        }
            
    }
}
