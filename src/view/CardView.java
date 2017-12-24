/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Card;
import model.Utils.Color;

/**
 *
 * @author Camilo
 */
public class CardView extends JLabel{
    private JPanel father = null;
    private Card card = null;
    private int cardWidth = 240;
    private int cardHeight = 360;
    private float scale= (float) 1;
    
    public CardView(){
        this.father = null;
        changeSize();
    }
    
    public CardView(JPanel father){
        this.father = father;
        changeSize();
    }
    
    public CardView(JPanel father,Card card){
        this.father = father;
        this.card = card;
        changeSize();
        //MouseListener Movido
    }
    
    public void changeSize(){
        ImageIcon imageIcon;
        int newWidth = (int) (cardWidth/scale);
        int newHeight = (int) (cardHeight/scale);
        
        this.setSize(newWidth, newHeight);
        
        if(card!= null){
            if(!card.getColor().equals(Color.NONE)){
                imageIcon = new ImageIcon("resources/"+card.getValue()+"_"+card.getColor()+".png");
            }else{
                imageIcon = new ImageIcon("resources/"+card.getValue()+".png");
            }
        }else{
            imageIcon = new ImageIcon("resources/background.png");
        }
            
        
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(newWidth ,newHeight ,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        setIcon(new ImageIcon(newimg));   // transform it back
    }
        
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        changeSize();
    }
    
    public int getCardWidth() {
        return cardWidth;
    }

    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }
    
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        changeSize();
    }

    public JPanel getFather() {
        return father;
    }

    public void setFather(JPanel parent) {
        this.father = parent;
    }
    
    
}
