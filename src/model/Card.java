/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import model.Utils.Color;
import model.Utils.Value;

/**
 *
 * @author Camilo
 */
public class Card {
    private Color color;
    private Value value;
    
    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card{" + "color=" + color + ", value=" + value + '}';
    }
    
}
