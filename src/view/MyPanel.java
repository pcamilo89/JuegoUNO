/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.FlowLayout;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Card;
import model.Core;
import model.Deck;
import model.Protocol;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class MyPanel extends JPanel{
    private JFrame father;
    private Deck hand;
    
    public MyPanel(){
        super();
        this.setLayout(new FlowLayout(1));
        this.setOpaque(false);
        this.father=null;
        this.hand = null;
    }
    
    public MyPanel(JFrame father, Deck hand){
        super();
        this.setLayout(new FlowLayout(1));
        this.setOpaque(false);
        this.father=father;
        this.hand= hand;
        
        updateHand();
    }

    public void updateHand(){
        this.removeAll();
        Card card;
        Iterator iterator = hand.getIterator();
        while(iterator.hasNext()){
            card = (Card) iterator.next();
            CardView cardView = new CardView(this,card);
            cardView.setScale((float)Utils.CARD_SCALE);
            cardView.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //LLAMADA A CONTROLADOR PARA JUGAR CARTA SELECCIONADA EN CASO DE TURNO
                    if(Core.getPhase() == Utils.PHASE_GAME){
                        //solo si es fase de juego
                        if(Core.isLocalTurn()){
                            Protocol.playCard(cardView.getCard());
                        }else{
                            System.out.println("NO ES TU TURNO");
                        }
                    }
                }
            });
            this.add(cardView);
        }
    }
    
    public JFrame getFather() {
        return father;
    }

    public void setFather(JFrame father) {
        this.father = father;
    }

    public Deck getHand() {
        return hand;
    }

    public void setHand(Deck hand) {
        this.hand = hand;
    }
    
    
}
