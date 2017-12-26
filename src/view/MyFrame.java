/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Camilo
 */
public class MyFrame extends JFrame{
    private JFrame father;
    
    public MyFrame(){
        super();
        this.father = null;
    }
    
    public MyFrame(JFrame father){
        this.father = father;
    }

    public JFrame getFather() {
        return father;
    }

    public void setFather(JFrame father) {
        this.father = father;
    }
    
    public void setAppIcon(){
        ImageIcon icon = new ImageIcon("resources/App_logo.png");
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(64 ,64 ,  java.awt.Image.SCALE_SMOOTH);
        this.setIconImage(newimg);
    }
}
