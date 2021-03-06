/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Dimension;
import javax.swing.JProgressBar;
import model.Core;
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
       mainView.setAppIcon();
       
       mainView.getjTFPort().setText(Utils.comPort);
       mainView.getjTFSpeed().setText(String.valueOf(Utils.BUFFER_SPEED));
       mainView.getjTFSleep().setText(String.valueOf(Utils.SLEEP_TIME_SHORT));
       
       mainView.getjBPortStop().setEnabled(false);
       mainView.getjBGameStart().setEnabled(false);
       mainView.getjPBCardCounter().setVisible(false);
       
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
        mainView.getjTFSpeed().setEnabled(false);
        mainView.getjTFSleep().setEnabled(false);
        mainView.getjCBSound().setEnabled(false);
        
        //se carga la configuracion en este momento
        if(mainView.getjCBSound().isSelected()){
            Utils.SOUND_ACTIVE = true;
        }
    }
    
    /**
     * Metodo que inicializa la comunicacion
     */
    public static void startComm(){
        Utils.comPort = mainView.getjTFPort().getText();
        Utils.BUFFER_SPEED = Integer.parseInt(mainView.getjTFSpeed().getText());
        Utils.SLEEP_TIME_SHORT = Integer.parseInt(mainView.getjTFSleep().getText());
        Utils.SLEEP_TIME_LONG = Utils.SLEEP_TIME_SHORT*5;
        if(Main.startApp()){
            MainViewController.setInfoLabel("Serial Comm Iniciado en: "+Utils.comPort);
            mainView.getjBGameStart().setEnabled(true);
            mainView.getjBPortStart().setEnabled(false);
            mainView.getjBPortStop().setEnabled(true);
        }            
    }
    
    /**
     * Metodo que detiene la comunicacion
     */
    public static void stopComm(){
        if(Main.stopApp()){
            MainViewController.setInfoLabel("Serial Comm Finalizado");
            mainView.getjBGameStart().setEnabled(false);
            mainView.getjBPortStart().setEnabled(true);
            mainView.getjBPortStop().setEnabled(false);
        }            
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
        gameView.setMinimumSize(new Dimension(600, 600));
        gameView.setSize(600, 600);
        gameView.setVisible(true);
    }
    
    public static void updateProgressBar(){
        JProgressBar bar = mainView.getjPBCardCounter();

            if(!bar.isVisible()){
                bar.setVisible(true);
            }
            
            int maxAmount = (Core.getMax()+1) * 7;
            
            int value = (int) (Core.getCardAmount()*100) / maxAmount;
            bar.setValue(value);
    }
}
