/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import javax.swing.JPanel;
import model.Core;
import model.Protocol;
import model.Utils;
import view.CardView;
import view.GameView;
import view.MyPanel;

/**
 *
 * @author Camilo
 */
public class GameViewController {
    private static GameView gameView;
    private static MyPanel panel;
    private static JPanel decks;
    
    public static void load(GameView from){
        GameViewController.gameView = from;
        gameView.setTitle(Utils.APP_NAME+" - Jugador "+Core.getLocal());
        
        panel =  new MyPanel(gameView, Core.getPlayer(Core.getLocal()));
        gameView.getjSPLocalHand().setViewportView(panel);
        
        gameView.setSize(Utils.GAMEVIEW_WIDTH, Utils.GAMEVIEW_HEIGHT);
        if(gameView.getFather()!=null){
            gameView.setLocationRelativeTo(gameView.getFather());
            gameView.getFather().setVisible(false);
        }
        
        decks = gameView.getjPDecks();
    }

    public static void updateTable(){
        GameViewController.getPanel().updateHand();
        GameViewController.updateDecks();
        gameView.validate();
        gameView.repaint();
    }
    
    public static void updateDecks(){
        decks.removeAll();
        
        CardView draw = new CardView(decks);
        draw.setScale((float)Utils.CARD_SCALE);
        draw.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //LLAMADA A CONTROLADOR PARA TOMAR CARTA DE DRAW
                if(!Core.isLocalAbleToPlay() && Core.isLocalTurn() && Core.getPlayer(Core.getLocal()).size()>0 && Core.getPhase() == Utils.PHASE_GAME){
                    //si local no es capaz de jugar ninguna de sus cartas locales
                    Protocol.takeCardToHand();
                    Core.setGrabCase(0);
                }
            }
        });
        decks.add(draw);
        
        if(Core.getDrop().size()>0){
            CardView drop = new CardView(decks,Core.getDrop().showLastCard());
            drop.setScale((float)Utils.CARD_SCALE);

            decks.add(drop);
        }
        
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
