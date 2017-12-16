/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Camilo
 */
public class MyDialog extends JDialog{
    private JLabel label = new JLabel();
    private JButton button = new JButton();
    private String message;
    
    public MyDialog(JFrame father,String tittle, String message, boolean modal){
        super(father, tittle, modal);        
        this.setLayout(new GridLayout(0, 1));
        this.setSize(300, 120);
        
        this. message = message;
        label.setText(this.message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        
        button.setText("Ok");
        button.addActionListener((ActionEvent e) -> {
            this.setVisible(false);
            this.dispose();
        });
        
        JPanel panel = new JPanel();
        panel.add(button);
        
        this.add(label);
        this.add(panel);
        
        this.setLocationRelativeTo(father);
        //this.setVisible(true);
    }

    public JButton getButton() {
        return button;
    }

    public void setButtonText(String text) {
        this.button.setText(text);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        label.setText(this.message);
    }
    
    
}
