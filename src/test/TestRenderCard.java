/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import model.Card;
import model.Deck;
import view.CardView;
import view.MyPanel;

/**
 *
 * @author Camilo
 */
public class TestRenderCard {
    public static void main(String[] args){
        MyPanel panel = new MyPanel();
        JFrame frame = new JFrame();
        CardView card = new CardView(panel);
        card.setScale((float)1.5);
        Deck deck = new Deck();
        deck.createDeck();
        
        
        frame.setSize(card.getCardWidth()+20, card.getCardHeight()+50);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(card);
        frame.add(panel);
        frame.setVisible(true);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestRenderCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Iterator iterator = deck.getIterator();
        Card mycard;
        while(iterator.hasNext()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestRenderCard.class.getName()).log(Level.SEVERE, null, ex);
            }
            mycard = (Card) iterator.next();
            card.setCard(mycard);
        }
    }
}
