/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 * Clase para el manejo del nucleo de la aplicacion
 * @author Camilo
 */
public class Core {
    //variable para fase del juego
    private static int phase = 0;
    private static int counter = 0;
    
    //variables para logica de turnos
    private static int local = -1;
    private static int actual = 0;
    private static int max = 3;
    private static int direction = 0;
    
    //variables de manejo de mazos
    private static Deck draw = new Deck();
    private static Deck drop = new Deck();
    
    //array de manos de jugadores
    private static Deck[] players = new Deck[4];

    /**
     * Metodo para obtener valor de la fase
     * @return int con valor numerico
     */
    public static int getPhase() {
        return phase;
    }

    /**
     * Metodo para asignar la fase
     * @param phase int con valor numerico
     */
    public static void setPhase(int phase) {
        Core.phase = phase;
    }

    /**
     * Metodo para obtener valor del contador
     * @return int con valor numerico
     */
    public static int getCounter() {
        return counter;
    }

    /**
     * Metodo para asignar valor del contador
     * @param counter int con valor numerico
     */
    public static void setCounter(int counter) {
        Core.counter = counter;
    }
    
    /**
     * Metodo que retorna la mano de un jugador
     * @param num numero de jugador dentro de la partida
     * @return Mazo de cartas
     */
    public static Deck getPlayer(int num) {
        return players[num];
    }

    /**
     * Metodo de inicializacion de arreglo de jugadores
     */
    public static void setPlayers() {
        for(int i=0;i<players.length;i++){
            Core.players[i] = new Deck();
        }
        
    }    
    
    /**
     * Metodo para obtener mazo Draw
     * @return mazo de cartas
     */
    public static Deck getDraw() {
        return draw;
    }

    /**
     * Metodo para Asignar mazo Draw
     * @param draw Mazo de cartas
     */
    public static void setDraw(Deck draw) {
        Core.draw = draw;
    }

    /**
     * Metodo para obtener mazo Drop
     * @return mazo de cartas
     */
    public static Deck getDrop() {
        return drop;
    }

    /**
     * Metodo para Asignar mazo Draw
     * @param drop Mazo de cartas
     */
    public static void setDrop(Deck drop) {
        Core.drop = drop;
    }
        
    /**
     * Metodo para obtener una carta especifica de mazo Draw retirandola del mismo
     * incluye metodo refill para recargar cartas cuando se encuentre vacio draw
     * @param color Color de la carta
     * @param value Valor de la carta
     * @return Carta del mazo
     */
    public static Card getCard(Utils.Color color,Utils.Value value){
        Card card = draw.getCard(color, value);
        if(draw.isEmpty()){
            refill();
        }
        return card;
    }
    
    /**
     * Metodo para obtener una carta aleatoria de mazo Draw retirandola del mismo
     * incluye metodo refill para recargar cartas cuando se encuentre vacio draw
     * @return Carta del mazo
     */
    public static Card getRandomCard(){
        Card card = draw.getRandomCard();
        if(draw.isEmpty()){
            refill();
        }
        return card;
    }
    
    /**
     * Metodo que pasa cartas del mazo drop al mazo draw cuando este se vacia
     */
    public static void refill(){
        Card card = drop.getLastCard();
        draw.setDeck(drop.getDeck());
        drop = new Deck();
        drop.addCard(card);
    }    
    
    /**
     * Metodo para obtener valor numerico del jugador Local
     * @return int con valor numerico
     */
    public static int getLocal() {
        return local;
    }

    /**
     * Metodo para asignar el valor numerico del jugador Local
     * @param local int con valor numerico
     */
    public static void setLocal(int local) {
        Core.local = local;
    }
    
    /**
     * Metodo para obtener el valor numerico del jugador Actual
     * @return int con valor numerico
     */
    public static int getActual() {
        return actual;
    }

    /**
     * Metodo para asignar el valor numerico del jugador Actual
     * @param actual int con valor numerico
     */
    public static void setActual(int actual) {
        Core.actual = actual;
    }

    /**
     * Metodo para obtener nuemero maximo de jugadores
     * @return int con valor numerico
     */
    public static int getMax() {
        return max;
    }

    /**
     * Metodo para asignar numero maximo de jugadores
     * @param max int con valor numerico
     */
    public static void setMax(int max) {
        Core.max = max;
    }

    /**
     * Metodo para obtener direccion de juego
     * @return int con valor numerico
     */
    public static int getDirection() {
        return direction;
    }

    /**
     * Metodo para asignar direccion de juego
     * @param direction int con valor numerico
     */
    public static void setDirection(int direction) {
        Core.direction = direction;
    }
    
    /**
     * Metodo para el cambio de direccion de juego
     */
    public static void changeDirection(){
        if(direction==0){
            direction=1;
        }else{
            direction=0;
        }
    }
    
    /**
     * Metodo para hacer ciclo de turnos
     */
    public static void nextTurn(){
        if(direction==0){
            actual++;
        }else if(direction==1){
            actual--;
        }
        if(actual==max+1){
            actual=0;
        }else if(actual==-1){
            actual=3;
        }
    }
    
    /**
     * Metodo para consultar si es turno del jugador Local
     * @return boolean true or false
     */
    public static boolean isLocalTurn(){
        if(local==actual){
            return true;
        }
        return false;
    }
    
}
