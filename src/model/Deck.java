/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import model.Utils.Color;
import model.Utils.Value;

/**
 *
 * @author Camilo
 */
public class Deck {
    private List<Card> myDeck = new ArrayList<>();
    
    public void fillDeck(){

        for (Color i : Color.values()) {
            for (Value j : Value.values()) {
                if(i!=Color.None && j==Value.Cero){
                    myDeck.add(new Card(i, j));
                }else if(i!=Color.None && j!=Value.CambiaColor && j!=Value.MasCuatro  && j!=Value.None){
                    myDeck.add(new Card(i, j));
                    myDeck.add(new Card(i, j));
                }else if(i==Color.None && (j==Value.CambiaColor || j==Value.MasCuatro)){
                    myDeck.add(new Card(i, j));
                    myDeck.add(new Card(i, j));
                    myDeck.add(new Card(i, j));
                    myDeck.add(new Card(i, j));
                }
            }
        }
    }
    
    public void clearDeck(){
        myDeck.clear();
    }
    
    public void showCards(){
        Iterator myIterator;
        myIterator = myDeck.iterator();
        Card card;
        while (myIterator.hasNext())
        {
            card = (Card)myIterator.next();
            System.out.println(card.toString());
        }
    }
    
    public Card getRandom(){
        Random randomGenerator = new Random();
        Card card = null;
        if(myDeck.size()>0){
            int index = randomGenerator.nextInt(myDeck.size());
            card = myDeck.get(index);
        }
      
        myDeck.remove(card);
        return card;
    }
    
    public Card getCard(Color color,Value value){
        
        Iterator myIterator;
        myIterator = myDeck.iterator();
        Card card = null;
        Card cardActual = null;
        while (myIterator.hasNext())
        {
            cardActual = (Card)myIterator.next();
            if(cardActual.getColor()==color && cardActual.getValue()==value){
                card = cardActual;
                break;
            }
        }
        if(card!= null)
            myDeck.remove(card);
        return card;
    }
    
    public int size(){
        return myDeck.size();
    }
}
