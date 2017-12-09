/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

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
    
}
