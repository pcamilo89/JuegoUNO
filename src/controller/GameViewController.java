/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
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
        
        gameView.getjLPlayers().setText("");
        gameView.getjLLocalInfo().setText("");
        gameView.getjLLastCard().setText("");
        
        GridBagLayout experimentLayout = new GridBagLayout();
        gameView.getjPColor().setLayout(experimentLayout);
    }

    public static void updateTable(){
        GameViewController.getPanel().updateHand();
        GameViewController.updateDecks();
        GameViewController.setLabelPlayers();
        GameViewController.setTableColor();
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
                    Core.nextTurn();
                    Core.setGrabCase(0);
                    GameViewController.setLocalInfo(Utils.INFO_MESSAGE_CARD_TAKE);
                }else{
                    GameViewController.setLocalInfo(Utils.INFO_MESSAGE_CARD_NOTAKE);
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
    
    public static void setLabelPlayers(){
        String text = "<html><b>Cartas</b><br>";
        for(int i=0; i <= Core.getMax() ; i++){
            int size = Core.getPlayer(i).size();
            if(i != Core.getLocal()){
                
                text += "<b>Jugador "+i+":</b> "+size;
                text += "<br>";
            }                
        }
        text += "<b>Draw: </b>"+Core.getDraw().size()+"<br>";
        text += "<b>Drop: </b>"+Core.getDrop().size()+"<br>";
        text += "</html>";
        gameView.getjLPlayers().setText(text);
    }
    
    public static void setTableColor(){
        JPanel colorpanel = gameView.getjPColor();
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5;
        c.ipady = 5;
        
        colorpanel.removeAll();
        if(Core.getDrop().showLastCard().getColor().equals(Utils.Color.NONE) && Core.getCardsPlayed() > 0){
            JLabel label = new JLabel("<html><b>Color: </b></html>");
            label.setSize(50, 20);
            
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            
            colorpanel.add(label,c);
            JPanel color = new JPanel();
            color.setSize(20,20);
            if(Utils.intToColor(Core.getTableColor()).equals(Utils.Color.AZUL)){
                color.setBackground(Color.BLUE);
            }else if (Utils.intToColor(Core.getTableColor()).equals(Utils.Color.VERDE)){
                color.setBackground(Color.GREEN);
            }else if (Utils.intToColor(Core.getTableColor()).equals(Utils.Color.ROJO)){
                color.setBackground(Color.RED);
            }else if (Utils.intToColor(Core.getTableColor()).equals(Utils.Color.AMARILLO)){
                color.setBackground(Color.YELLOW);
            }
            
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 0;
            
            
            colorpanel.add(color,c);
        }
        String text = "<html><b>Sentido: </b>";
        if(Core.getDirection() == 0){
            text += "Ascendente";
        }else{
            text += "Descendente";
        }
        text += "</html>";
        
        JLabel sentido = new JLabel(text);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
                
        colorpanel.add(sentido,c);

    }
    
    public static void setLocalInfo(String text){
        gameView.getjLLocalInfo().setText(text);
    }
    
    public static void setLastCard(String text){
        gameView.getjLLastCard().setText(text);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(GameViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //gameView.getjLLastCard().setText("");
    }
}
