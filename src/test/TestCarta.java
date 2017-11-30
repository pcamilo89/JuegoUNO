/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import model.Card;
import model.Deck;
import model.Utils;
import model.Utils.Color;
import model.Utils.Value;

/**
 *
 * @author Camilo
 */
public class TestCarta {
    public static void main(String[] args){
        //ejemplo creando una carta
        Card carta =  new Card(Utils.Color.ROJO, Utils.Value.SIETE);
        System.out.println(carta);
        System.out.println("");
        
        //ejemplo de creacion del mazo
        Deck myDeck = new Deck();
        myDeck.createDeck();
        //myDeck.showCards();
        System.out.println(myDeck.size());
        System.out.println("");
        
        //obtener carta al azar
        System.out.println(myDeck.getRandomCard());
        System.out.println(myDeck.size());
        System.out.println("");
        
        //obtener todas las cartas al azar
        for(int i=0;i<10;i++){
            System.out.println(myDeck.getRandomCard());
            System.out.println(myDeck.size());
        }
        System.out.println("");
        
        //obtener carta especifica
        System.out.println(myDeck.getCard(Utils.Color.ROJO, Utils.Value.SIETE));
        System.out.println(myDeck.getCard(Utils.Color.ROJO, Utils.Value.SIETE));
        System.out.println(myDeck.getCard(Utils.Color.ROJO, Utils.Value.SIETE));
        System.out.println(myDeck.size());
        //myDeck.showCards();
        System.out.println("");
        
        //limpiar mazo
        myDeck.clearDeck();
        System.out.println(myDeck.size());
        
        //prueba de conversiones
        System.out.println("Prueba Colores:");
        for(Color i: Color.values()){
            int test = Utils.colorToInt(i);
            System.out.println(test+" "+Utils.intToBinary(test, 2));
        }
        for(int i=0;i<5;i++){
            Color color = Utils.intToColor(i);
            System.out.println(i+" "+color);
        }
        
        System.out.println("Prueba Valores:");
        for(Value i: Value.values()){
            int test = Utils.valueToInt(i);
            System.out.println(test+" "+Utils.intToBinary(test, 4));
        }
        for(int i=0;i<16;i++){
            Value value = Utils.intToValue(i);
            System.out.println(i+" "+value);
        }
    }
}
