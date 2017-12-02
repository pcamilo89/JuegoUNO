/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import model.Card;
import model.Core;
import model.Utils;

public class TestRecargaMazo {

    public static void main(String[] args){
        //se inicializan ambos mazos

        
        //se llena el mazo draw
        Core.getDraw().createDeck();
        
        System.out.println("Cartas de Draw: "+Core.getDraw().size());
        
        System.out.println("Cartas de Drop: ");
        Core.getDrop().addCard(Core.getDraw().getFirstCard());
        Core.getDrop().addCard(Core.getDraw().getFirstCard());
        Core.getDrop().addCard(Core.getDraw().getLastCard());
        Core.getDrop().addCard(Core.getDraw().getFirstCard());
        Core.getDrop().printDeck();
        
        

        while(Core.getDraw().size()>1){
            Core.getDrop().addCard(Core.getDraw().getRandomCard());
        }
        System.out.println("Cartas de Draw: "+Core.getDraw().size());
        
        Core.getDrop().addCard(Core.getDraw().getFirstCard());
        System.out.println("Cartas de Draw Antes: "+Core.getDraw().size());
        if(Core.getDraw().isEmpty()){
            System.out.println("Draw esta vacio.");
            Core.refill();
        }
        System.out.println("Cartas de Draw Despues: "+Core.getDraw().size());
        System.out.println("Cartas de Drop Despues: "+Core.getDrop().size());
        Core.getDrop().printDeck();
        
        System.out.println("Cartas Draw al final de todo");
        Core.getDraw().printDeck();
        
        System.out.println("\n\nPrueba de dos metodos y no uno solo");
        Card card = Core.getDraw().showCard(Utils.Color.ROJO, Utils.Value.DOS);
        System.out.println(Core.getDraw().removeCard(card));
        card = Core.getDraw().showCard(Utils.Color.ROJO, Utils.Value.DOS);
        System.out.println(Core.getDraw().removeCard(card));
        System.out.println(Core.getDraw().removeCard(card));
        System.out.println(card);
    }
}
