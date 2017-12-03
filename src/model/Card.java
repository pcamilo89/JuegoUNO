/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import model.Utils.Color;
import model.Utils.Value;

/**
 * Clase para el manejo de cartas UNO
 * @author Camilo
 */
public class Card {
    private Color color;
    private Value value;
    
    /**
     * Constructor de carta (ver utils para referencia de valores)
     * @param color recibe color de la carta
     * @param value recibe valor de la carta
     */
    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Metodo para obtener Color
     * @return Color de la carta
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metodo para asignar color
     * @param color Color de la carta
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Metodo para obtener valor de la carta
     * @return Valor de la carta
     */
    public Value getValue() {
        return value;
    }

    /**
     * Metodo para asignar valor de la carta
     * @param value valor de la carta
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Metodo para imprimir una carta como string
     * @return String con informacion de la carta
     */
    @Override
    public String toString() {
        return "Card{" + "color=" + color + ", value=" + value + '}';
    }
    
    /**
     * Metodo que chequea si la carta es +4 o cambia color que tenga el color correcto.
     */
    public void checkCard(){
        if(value.equals(Value.CAMBIA_COLOR)||value.equals(Value.MAS_CUATRO)){
            color = Color.NONE;
        }
    }
}
