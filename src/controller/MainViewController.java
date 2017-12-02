/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.Protocol;
import model.Utils;
import view.Main;
import view.MainView;

/**
 *
 * @author Camilo
 */
public class MainViewController {
    private static MainView mainView;
    
    public static void load(MainView from){
       mainView = from;
       mainView.setTitle(Utils.APP_NAME);
       mainView.getjTFPort().setText(Utils.comPort);
       mainView.getjBGameStart().setEnabled(false);
    }
    
    public static void disableButtons(){
        mainView.getjBGameStart().setEnabled(false);
        mainView.getjBPortStart().setEnabled(false);
        mainView.getjBPortStop().setEnabled(false);
    }
    
    public static void startComm(){
        Utils.comPort = mainView.getjTFPort().getText();
        Main.startApp();
        mainView.getjBGameStart().setEnabled(true);
    }
    
    public static void stopComm(){
        Main.stopApp();
        mainView.getjBGameStart().setEnabled(false);
    }
    
    public static void startGame(){
        Protocol.startGame();
        disableButtons();
    }
    
    public static void setInfoLabel(String text){
        mainView.getjLInfo().setText(text);
    }
    
    public static void clearInfoLabel(){
        mainView.getjLInfo().setText("");
    }
    
    public static void setCardsLabel(String text){
        mainView.getjLCards().setText(text);
    }
    
    public static void clearCardsLabel(){
        mainView.getjLCards().setText("");
    }
    
    public static void setPlayerLabel(String text){
        mainView.getjLPlayer().setText(text);
    }
    
    public static void clearPlayerLabel(){
        mainView.getjLPlayer().setText("");
    }
}
