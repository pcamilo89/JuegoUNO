/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Iterator;

/**
 * Clase para el manejo del nucleo de la aplicacion
 * @author Camilo
 */
public class Core {
    //variable para fase del juego
    private static int phase = 0;
    //contador de cartas para cartas iniciales o casos +2 y +4
    private static int counter = 0;
    
    //variables para logica de turnos
    private static int local = -1;
    private static int actual = 0;
    private static int max = 3;
    private static int direction = 0;
    
    //variables para partida en curso
    private static int cardsPlayed = -1;
    private static int tableColor = -1;
    
    //variable para controlar casos en los que se tiene que agarrar cartas
    //0 - agarrar carta y pasar turno en caso de no tener cartas para jugar en mano
    //1 - agarrar cartas por +2 o +4 para lo que se utiliza counter para numero de cartas
    private static int grabCase = -1;
    
    //variables de manejo de mazos
    private static Deck draw = new Deck();
    private static Deck drop = new Deck();
    
    //array de manos de jugadores
    private static final Deck[] players = new Deck[4];

    public static int getGrabCase() {
        return grabCase;
    }

    public static void setGrabCase(int grabCase) {
        Core.grabCase = grabCase;
    }
    
    public static int getTableColor() {
        return tableColor;
    }

    public static void setTableColor(int tableColor) {
        Core.tableColor = tableColor;
    }

    public static int getCardsPlayed() {
        return cardsPlayed;
    }

    public static void setCardsPlayed(int cardsPlayed) {
        Core.cardsPlayed = cardsPlayed;
    }

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
    
    public static Deck[] getPlayers() {
        return players;
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
     * Metodo que obtiene carta aleatoria para el inicio de partida de mazo draw
     * @return carta del mazo
     */
    public static Card getRandomInitialCard(){
        Card card = draw.getRandomInitialCard();
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
            actual=max;
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
    
    /**
     * Metodo para imprimir la mesa 
     */
    public static void printTable(){
        
        for(int i=0;i<players.length;i++){
            System.out.println("Player: "+i);
            players[i].printDeck();
        }
        System.out.println("Drop: ");
        getDrop().printDeck();
        getDrop().size();
    }
    
    public static boolean isLocalAbleToPlay(){
        Deck deck = Core.getPlayer(Core.local);
        Iterator myIterator = deck.getIterator();
        Card card;
        while(myIterator.hasNext()){
            card = (Card) myIterator.next();
            if(card.getColor().equals(Utils.intToColor(Core.getTableColor())) || card.getValue().equals(getDrop().showLastCard().getValue()) || card.getColor().equals(Utils.Color.NONE)){
                return true;
            }
        }        
        return false;
    }
    
    /**
     * Metodo para chequear si se tiene el color de la mesa en la mano
     * @return boolean true or false
     */
    public static boolean isColorInHand(){
        Deck deck = Core.getPlayer(Core.getLocal());
        Iterator iter = deck.getIterator();
        
        while(iter.hasNext()){
            Card card = (Card) iter.next();
            if(Utils.colorToInt(card.getColor()) == Core.getTableColor()){
                return true;
            }
        }
        return false;
    }
    
    public static int winerPoints(){
        int points = 0;
        
        for(int i=0;i<=Core.getMax();i++){
            Deck deck = getPlayer(i);
            if (deck.size() > 0){
                Iterator iter = deck.getIterator();
                while(iter.hasNext()){
                    Card card = (Card) iter.next();
                    int valor = Utils.valueToInt(card.getValue());
                    if(valor <= 9){
                        points += valor;
                    }else if(valor >= 10 && valor <= 12){
                        points += 20;                    
                    }else if(valor >= 13 && valor <= 14){
                        points += 20;
                    }
                }
            }
        }
        
        return points;
    }
    
}
