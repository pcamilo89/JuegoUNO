/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.Protocol;
import model.Utils;
import view.GameView;
import view.Main;
import view.MainView;

/**
 * Clase de control de la ventana inicial de la aplicacion
 * @author Camilo
 */
public class MainViewController {
    /**
     * Variable mainView que hace referencia a la interfaz
     */
    private static MainView mainView;
    
    /**
     * Metodo de carga inicial de la interfaz
     * @param from referencia a la interfaz
     */
    public static void load(MainView from){
       mainView = from;
       mainView.setTitle(Utils.APP_NAME);
       
       mainView.getjTFPort().setText(Utils.comPort);
       mainView.getjTFSpeed().setText(String.valueOf(Utils.BUFFER_SPEED));
       mainView.getjBGameStart().setEnabled(false);
       
       mainView.setLocationRelativeTo(null);
       mainView.setResizable(false);
    }
    
    /**
     * Metodo que desabilita la interfaz luego del inicio del juego
     */
    public static void disableButtons(){
        mainView.getjBGameStart().setEnabled(false);
        mainView.getjBPortStart().setEnabled(false);
        mainView.getjBPortStop().setEnabled(false);
        mainView.getjTFPort().setEnabled(false);
    }
    
    /**
     * Metodo que inicializa la comunicacion
     */
    public static void startComm(){
        Utils.comPort = mainView.getjTFPort().getText();
        Utils.BUFFER_SPEED = Integer.parseInt(mainView.getjTFSpeed().getText());
        Main.startApp();
        mainView.getjBGameStart().setEnabled(true);
    }
    
    /**
     * Metodo que detiene la comunicacion
     */
    public static void stopComm(){
        Main.stopApp();
        mainView.getjBGameStart().setEnabled(false);
    }
    
    /**
     * Metodo de inicio de partida
     */
    public static void startGame(){
        Protocol.startGame();
        disableButtons();
    }
    
    /**
     * Metodo para asignar informacion a la Label de informacion
     * @param text texto a mostrar
     */
    public static void setInfoLabel(String text){
        mainView.getjLInfo().setText(text);
    }
    
    /**
     * Metodo para limpiar Label de informacion
     */
    public static void clearInfoLabel(){
        mainView.getjLInfo().setText("");
    }
    
    /**
     * Metodo para asignar informaicon a Label Cartas
     * @param text texto a mostrar
     */
    public static void setCardsLabel(String text){
        mainView.getjLCards().setText(text);
    }
    
    /**
     * Metodo para limpiar Label Cartas
     */
    public static void clearCardsLabel(){
        mainView.getjLCards().setText("");
    }
    
    /**
     * Metodo para asignar informaicon a Label Players
     * @param text texto a mostrar
     */
    public static void setPlayerLabel(String text){
        mainView.getjLPlayer().setText(text);
    }
    
    /**
     * Metodo para limpiar Label Players
     */
    public static void clearPlayerLabel(){
        mainView.getjLPlayer().setText("");
    }
    
    /**
     * Metodo para asignar informacion a label Turn
     * @param text texto a mostrar
     */
    public static void setTurnLabel(String text){
        mainView.getjLTurn().setText(text);
    }
    
    /**
     * Metodo para limpiar Label Turn
     */
    public static void clearTurnLabel(){
        mainView.getjLTurn().setText("");
    }
    
    public static void showGameView(){
        GameView gameView = new GameView(mainView);
        gameView.setVisible(true);
    }
}
