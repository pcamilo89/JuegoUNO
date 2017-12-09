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
    private List<Card> deck = new ArrayList<>();

    /**
     * Metodo para obtener el mazo
     * @return Mazo de cartas
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Metodo para asignar el mazo
     * @param myDeck Mazo de cartas
     */
    public void setDeck(List<Card> myDeck) {
        this.deck = myDeck;
    }
    
    /**
     * Metodo para agregar una carta al mazo
     * @param card carta que sera agregada
     */
    public void addCard(Card card){
        deck.add(card);
    }
    
    /**
     * Metodo para inicializar mazo de cartas con las 108 cartas del juego UNO
     */
    public void createDeck(){

        for (Color i : Color.values()) {
            for (Value j : Value.values()) {
                if(i!=Color.NONE && j==Value.CERO){
                    deck.add(new Card(i, j));
                }else if(i!=Color.NONE && j!=Value.CAMBIA_COLOR && j!=Value.MAS_CUATRO  && j!=Value.NONE){
                    deck.add(new Card(i, j));
                    deck.add(new Card(i, j));
                }else if(i==Color.NONE && (j==Value.CAMBIA_COLOR || j==Value.MAS_CUATRO)){
                    deck.add(new Card(i, j));
                    deck.add(new Card(i, j));
                    deck.add(new Card(i, j));
                    deck.add(new Card(i, j));
                }
            }
        }
    }
    
    /**
     * Metodo para limpiar el mazo
     */
    public void clearDeck(){
        deck.clear();
    }
    
    public Iterator getIterator(){
        return deck.iterator();
    }
    
    /**
     * Metodo que imprime las cartas del mazo
     */
    public void printDeck(){
        Iterator myIterator;
        myIterator = deck.iterator();
        Card card;
        while (myIterator.hasNext())
        {
            card = (Card) myIterator.next();
            if(card!=null){
                System.out.println(card.toString());
                System.out.flush();
            }
        }
    }
    
    /**
     * Metodo que muestra la ultima carta o carta tope sin retirarla del mazo
     * @return carta a mostrar
     */
    public Card showLastCard(){
        Card card = deck.get(size()-1);
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
        if(deck.size()>0){
            int index = randomGenerator.nextInt(deck.size());
            card = deck.get(index);
        }
        
        if(card!=null){
            while(removeCard(card)){
                if(card.getValue().equals(Value.MAS_CUATRO)||card.getValue().equals(Value.CAMBIA_COLOR)){
                    
                    //SETEO DE COLOR EN DECK GET RANDOM
                    System.out.println("CARTA SIN COLOR EN CARD DECK GET RANDOM");
                    card.setColor(Color.VERDE);

                }
            }
        }
        return card;
    }
    
    /**
     * Metodo que muestra si una carta especifica se encuentra en el mazo
     * @param color Color de la carta
     * @param value Valor de la carta
     * @return Carta si fue encontrada o null
     */
    public Card showCard(Color color,Value value){
     
        //cambiar color para carta +4 y CambiaColor
        if(value.equals(Value.CAMBIA_COLOR)||value.equals(Value.MAS_CUATRO)){
            color = Color.NONE;
        }
        //Card cardActual;
        Card card = null;
        for (Card i :  this.deck) {
            if(i.getColor().equals(color) && i.getValue().equals(value)){
                card = i;
                break;
            }
        }
        return card;
    }
    
    /**
     * Metodo que remueve una carta del mazo
     * @param card carta a ser removida
     * @return boolean true or false
     */
    public boolean removeCard(Card card){
        return deck.remove(card);
    }
    
    /**
     * Metodo para obtener la carta especificada del mazo retirandola del mismo
     * @param color Color de la carta
     * @param value Valor de la carta
     * @return Carta proveiente del mazo
     */
    public Card getCard(Color color,Value value){
        Card card = showCard(color, value);

        while(removeCard(card))
            return card;
        return null;
    }
    
    /**
     * Tamaño del mazo
     * @return int de tamaño
     */
    public int size(){
        return deck.size();
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
        Card card = deck.get(0);
        while(deck.remove(card))
            return card;
        return null;
    }
    /**
     * Metodo que obtiene la ultima carta o carta tope del Mazo y la retira del mismo
     * @return Carta que se encontraba en la ultima posicion o al tope
     */
    public Card getLastCard(){
        Card card = deck.get(size()-1);
        while(deck.remove(card))
            return card;
        return null;
    }
}
