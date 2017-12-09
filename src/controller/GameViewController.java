/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.Core;
import model.Utils;
import view.GameView;
import view.MyPanel;

/**
 *
 * @author Camilo
 */
public class GameViewController {
    private static GameView gameView;
    private static MyPanel panel;
    
    public static void load(GameView from){
        GameViewController.gameView = from;
        panel =  new MyPanel(gameView, Core.getPlayer(Core.getLocal()));
        gameView.getjSPLocalHand().setViewportView(panel);
        
        gameView.setSize(Utils.GAMEVIEW_WIDTH, Utils.GAMEVIEW_HEIGHT);
        if(gameView.getFather()!=null){
            
            gameView.setLocationRelativeTo(gameView.getFather());
            
            gameView.getFather().setVisible(false);
            
            //gameView.getFather().dispose();
            //gameView.setFather(null);
        }
    }

    public static void updateTable(){
        GameViewController.getPanel().updateHand();
        
        gameView.validate();
        gameView.repaint();
    }
    
    public static GameView getGameView() {
        return gameView;
    }

    public static void setGameView(GameView gameView) {
        GameViewController.gameView = gameView;
    }

    public static MyPanel getPanel() {
        return panel;
    }

    public static void setPanel(MyPanel panel) {
        GameViewController.panel = panel;
    }
    
}
