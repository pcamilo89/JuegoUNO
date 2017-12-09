/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.swing.JFrame;
import model.Card;
import model.Deck;
import view.MyPanel;

/**
 *
 * @author Camilo
 */
public class TestRenderDeck {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 200);
        
        Deck mydeck = new Deck();
        mydeck.createDeck();
        
        Deck hand = new Deck();
        Card card;
        for(int i=0;i<7;i++){
            card = mydeck.getRandomCard();
            card.checkCard();
            hand.addCard(card);
        }
        
        MyPanel panel = new MyPanel(frame,hand);
        frame.add(panel);
        frame.setVisible(true);
    }
}
