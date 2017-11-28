/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import model.Card;
import model.Deck;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class TestCarta {
    public static void main(String[] args){
        //ejemplo creando una carta
        Card carta =  new Card(Utils.Color.Rojo, Utils.Value.Siete);
        System.out.println(carta);
        System.out.println("");
        
        //ejemplo de creacion del mazo
        Deck myDeck = new Deck();
        myDeck.fillDeck();
        //myDeck.showCards();
        System.out.println(myDeck.size());
        System.out.println("");
        
        //obtener carta al azar
        System.out.println(myDeck.getRandom());
        System.out.println(myDeck.size());
        System.out.println("");
        
        //obtener todas las cartas al azar
        for(int i=0;i<10;i++){
            System.out.println(myDeck.getRandom());
            System.out.println(myDeck.size());
        }
        System.out.println("");
        
        //obtener carta especifica
        System.out.println(myDeck.getCard(Utils.Color.Rojo, Utils.Value.Siete));
        System.out.println(myDeck.getCard(Utils.Color.Rojo, Utils.Value.Siete));
        System.out.println(myDeck.getCard(Utils.Color.Rojo, Utils.Value.Siete));
        System.out.println(myDeck.size());
        //myDeck.showCards();
        System.out.println("");
        
        //limpiar mazo
        myDeck.clearDeck();
        System.out.println(myDeck.size());
    }
}
