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
 * Clase para el manejo del mazo
 * @author Camilo
 */
public class Deck {
    private List<Card> Deck = new ArrayList<>();

    /**
     * Metodo para obtener el mazo
     * @return Mazo de cartas
     */
    public List<Card> getDeck() {
        return Deck;
    }

    /**
     * Metodo para asignar el mazo
     * @param myDeck Mazo de cartas
     */
    public void setDeck(List<Card> myDeck) {
        this.Deck = myDeck;
    }
    
    /**
     * Metodo para agregar una carta al mazo
     * @param card carta que sera agregada
     */
    public void addCard(Card card){
        Deck.add(card);
    }
    
    /**
     * Metodo para inicializar mazo de cartas con las 108 cartas del juego UNO
     */
    public void createDeck(){

        for (Color i : Color.values()) {
            for (Value j : Value.values()) {
                if(i!=Color.NONE && j==Value.CERO){
                    Deck.add(new Card(i, j));
                }else if(i!=Color.NONE && j!=Value.CAMBIA_COLOR && j!=Value.MAS_CUATRO  && j!=Value.NONE){
                    Deck.add(new Card(i, j));
                    Deck.add(new Card(i, j));
                }else if(i==Color.NONE && (j==Value.CAMBIA_COLOR || j==Value.MAS_CUATRO)){
                    Deck.add(new Card(i, j));
                    Deck.add(new Card(i, j));
                    Deck.add(new Card(i, j));
                    Deck.add(new Card(i, j));
                }
            }
        }
    }
    
    /**
     * Metodo para limpiar el mazo
     */
    public void clearDeck(){
        Deck.clear();
    }
    
    /**
     * Metodo que imprime las cartas del mazo
     */
    public void showCards(){
        Iterator myIterator;
        myIterator = Deck.iterator();
        Card card;
        while (myIterator.hasNext())
        {
            card = (Card)myIterator.next();
            System.out.println(card.toString());
        }
    }
    
    /**
     * Metodo que muestra la ultima carta o carta tope sin retirarla del mazo
     * @return 
     */
    public Card showLastCard(){
        Card card = Deck.get(size()-1);
        System.out.println(card);
        return card;
    }
    
    /**
     * Metodo para obtener una carta aleatoriamente del mazo retirandola del mismo
     * @return Carta proveniente del mazo
     */
    public Card getRandomCard(){
        Random randomGenerator = new Random();
        Card card = null;
        if(Deck.size()>0){
            int index = randomGenerator.nextInt(Deck.size());
            card = Deck.get(index);
        }
      
        Deck.remove(card);
        return card;
    }
    
    /**
     * Metodo para obtener la carta especificada del mazo retirandola del mismo
     * @param color Color de la carta
     * @param value Valor de la carta
     * @return Carta proveiente del mazo
     */
    public Card getCard(Color color,Value value){
        
        Iterator myIterator;
        myIterator = Deck.iterator();
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
            Deck.remove(card);
        return card;
    }
    
    /**
     * Tamaño del mazo
     * @return int de tamaño
     */
    public int size(){
        return Deck.size();
    }
    
    /**
     * Metodo consulta si el mazo esta vacio
     * @return boolean true or false
     */
    public boolean isEmpty(){
        if (size()==0){
            return true;
        }
        return false;
    }
    
    /**
     * Metodo que obtiene la primera carta en el mazo y la retira, tambien considerada la carta de index[0]
     * @return  Carta que se encontraba en la primera posicion o al fondo
     */
    public Card getFirstCard(){
        Card card = Deck.get(0);
        Deck.remove(card);
        return card;
    }
    /**
     * Metodo que obtiene la ultima carta o carta tope del Mazo y la retira del mismo
     * @return Carta que se encontraba en la ultima posicion o al tope
     */
    public Card getLastCard(){
        Card card = Deck.get(size()-1);
        Deck.remove(card);
        return card;
    }
}
