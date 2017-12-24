/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Camilo
 */
public class TestRenderHand {
    
    public static class HandPanel extends JPanel{
        private BufferedImage card;


        private final int cardWidth = 240;
        private final int cardHeight = 360;
        private float scale= (float) 2.5;
        
        private int amount = 1;
        
        private int cwidth = (int) (cardWidth/scale);
        private int cheight = (int) (cardHeight/scale);
        
        private int carea;
        
        private int coordx = 10;
        private int coordy = 10;
        
        private int orientation = 0;
        
        public HandPanel(float scale, int orientation){
            super();
            ImageIcon imageIcon;
            imageIcon = new ImageIcon("resources/background.png");
            
            BufferedImage bi = new BufferedImage(
            imageIcon.getIconWidth(),
            imageIcon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            // paint the Icon to the BufferedImage.
            imageIcon.paintIcon(null, g, 0,0);
            g.dispose();
            
            card = bi;
            this.scale = scale;
            this.orientation = orientation;
        }
        
        public void setData(int amount){
            
            if(orientation == 0){
                int center = (int) (this.getWidth()/2);

                int div = 2;
                this.amount = amount;

                carea = (cwidth/div);

                int tarea = cwidth + (carea*(amount-1));

                while(tarea > this.getWidth() && tarea > cwidth){
                    div++;
                    carea = (cwidth/div);
                    tarea = cwidth + (carea*(amount-1));
                    System.out.println("Changed");
                }

                tarea /= 2;

                int initpos = center - (cwidth/2);
                if(amount > 1){
                    initpos = center - tarea;
                }

                coordx = initpos;
            }else if(orientation == 1){
                int center = (int) (this.getHeight()/2);

                int div = 2;
                this.amount = amount;

                carea = (cheight/div);

                int tarea = cheight + (carea*(amount-1));

                while(tarea > this.getHeight() && tarea > cheight){
                    div++;
                    carea = (cheight/div);
                    tarea = cheight + (carea*(amount-1));
                    System.out.println("Changed "+div);
                }

                tarea /= 2;

                int initpos = center - (cheight/2);
                if(amount > 1){
                    initpos = center - tarea;
                }

                coordy = initpos;
            }
                
        }
        
        public void painter(){
            
            Graphics g = this.getGraphics();
            
            for(int i=0;i<amount;i++){
                g.drawImage(card, coordx, coordy, cwidth, cheight, this);
                if(orientation == 0){
                    coordx += carea;
                }else if(orientation == 1){
                    coordy += carea;
                }
            }
            
            //g.setColor(Color.RED);
            //g.fillRect((width/2)-(size/2), 10, 10, 10);
            this.validate();
            
            
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
        }
        
        
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        HandPanel panel = new HandPanel((float)3,0);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);
        
        
        //panel.setBackground(Color.WHITE);

        
        frame.add(panel);
        frame.setVisible(true);
        
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestRenderHand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        panel.setData(8);
        panel.painter();
        
        panel.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(TestRenderHand.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    
                    panel.setData(8);
                    panel.painter();
                }
            });
               
       
        System.out.println("size: "+panel.getWidth()+" "+panel.getHeight());
    }
}
