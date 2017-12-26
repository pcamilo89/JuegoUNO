/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Core;
import model.Protocol;
import model.Utils;
import view.CardView;
import view.GameView;
import view.HandPanel;
import view.MyPanel;

/**
 *
 * @author Camilo
 */
public class GameViewController {
    private static GameView gameView;
    private static MyPanel panel;
    private static JPanel decks;
    
    private static HandPanel handUp;
    private static HandPanel handLeft;
    private static HandPanel handRight;
    
    private static JLabel jLUpCardNumber;
    private static JLabel jLLeftCardNumber;
    private static JLabel jLRightCardNumber;
    
    private static JLabel jLUpCheck;
    private static JLabel jLLeftCheck;
    private static JLabel jLRightCheck;
    
    private static int dir = 1;
    
    public static void load(GameView from){
        GameViewController.gameView = from;
        gameView.setTitle(Utils.APP_NAME+" - Jugador "+Core.getLocal());
        gameView.setAppIcon();
        
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
        
        //seteando las manos de otros jugadores en la interfaz
        handUp = new HandPanel(0);
        gameView.getjPUp().add(handUp);
        handUp.setBounds(0, 0, gameView.getjPUp().getWidth(), gameView.getjPUp().getHeight());
        
        handLeft = new HandPanel(1);
        gameView.getjPLeft().add(handLeft);
        handLeft.setBounds(0, 0, gameView.getjPLeft().getWidth(), gameView.getjPLeft().getHeight());
        
        handRight = new HandPanel(1);
        gameView.getjPRight().add(handRight);
        handRight.setBounds(0, 0, gameView.getjPRight().getWidth(), gameView.getjPRight().getHeight());
        
        //seteo de paneles de informacion de oponentes
        jLUpCardNumber = new JLabel("x");
        jLUpCardNumber.setHorizontalAlignment(JLabel.CENTER);
        jLUpCardNumber.setVerticalAlignment(JLabel.CENTER);
        
        jLLeftCardNumber  = new JLabel("x");
        jLLeftCardNumber.setHorizontalAlignment(JLabel.CENTER);
        jLLeftCardNumber.setVerticalAlignment(JLabel.CENTER);
        
        jLRightCardNumber  = new JLabel("x");
        jLRightCardNumber.setHorizontalAlignment(JLabel.CENTER);
        jLRightCardNumber.setVerticalAlignment(JLabel.CENTER);
        
        //seteo de label con check de turno
        ImageIcon imageIcon = new ImageIcon("resources/check.png");
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(20 ,20 ,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        //setIcon(new ImageIcon(newimg));   // transform it back
        
        jLUpCheck = new JLabel(new ImageIcon(newimg));
        jLLeftCheck = new JLabel(new ImageIcon(newimg));
        jLRightCheck = new JLabel(new ImageIcon(newimg));
        
        //seteo de label de direccion inicial
        updateDirection();

        //seteo de numero de cartas y check de turno
        GridLayout gridLayout = new GridLayout(0,2);
        gameView.getjPUPInfo().setLayout(gridLayout);
        
        JPanel jPUPCards = new JPanel();
        jPUPCards.setSize(20,20);
        jPUPCards.setLayout(null);
        jPUPCards.add(jLUpCardNumber);
        jLUpCardNumber.setBounds(0, 0, jPUPCards.getWidth(), jPUPCards.getHeight());
        jPUPCards.setBorder(BorderFactory.createRaisedBevelBorder());
        gameView.getjPUPInfo().add(jPUPCards);
        gameView.getjPUPInfo().add(jLUpCheck);
        
        gridLayout = new GridLayout(2,0);
        gameView.getjPLeftInfo().setLayout(gridLayout);
        
        JPanel jPleftCards = new JPanel();
        jPleftCards.setSize(20,20);
        jPleftCards.setLayout(null);        
        jPleftCards.add(jLLeftCardNumber);
        jLLeftCardNumber.setBounds(0, 0, jPleftCards.getWidth(), jPleftCards.getHeight());        
        jPleftCards.setBorder(BorderFactory.createRaisedBevelBorder());
        gameView.getjPLeftInfo().add(jPleftCards);
        gameView.getjPLeftInfo().add(jLLeftCheck);
        
        gameView.getjPRightInfo().setLayout(gridLayout);
        
        JPanel jPRightCards = new JPanel();
        jPRightCards.setSize(20,20);
        jPRightCards.setLayout(null);        
        jPRightCards.add(jLRightCardNumber);
        jLRightCardNumber.setBounds(0, 0, jPRightCards.getWidth(), jPRightCards.getHeight());        
        jPRightCards.setBorder(BorderFactory.createRaisedBevelBorder());
        gameView.getjPRightInfo().add(jPRightCards);
        gameView.getjPRightInfo().add(jLRightCheck);
        
        gameView.getjPUPInfo().setVisible(false);
        gameView.getjPLeftInfo().setVisible(false);
        gameView.getjPRightInfo().setVisible(false);
        
        
        //dependiendo de la cantidad de jugadores se utilizan diferentes paneles
        //se aprovecha logica de turnos
        int act = Core.getActual();
        
        if(Core.getMax()==1){     
            Core.setActual(Core.getLocal());
            Core.nextTurn();
            handUp.setPlayer(Core.getActual());
            handUp.setPlayerCards();
            handUp.cardDraw();
            gameView.getjPUPInfo().setVisible(true);
        }else if(Core.getMax()==2){     
            Core.setActual(Core.getLocal());
            Core.nextTurn();
            handLeft.setPlayer(Core.getActual());
            handLeft.setPlayerCards();
            handLeft.cardDraw();
            gameView.getjPLeftInfo().setVisible(true);
            Core.nextTurn();
            handRight.setPlayer(Core.getActual());
            handRight.setPlayerCards();
            handRight.cardDraw();
            gameView.getjPRightInfo().setVisible(true);            
        }else if(Core.getMax()==3){     
            Core.setActual(Core.getLocal());
            Core.nextTurn();
            handLeft.setPlayer(Core.getActual());
            handLeft.setPlayerCards();
            handLeft.cardDraw();
            gameView.getjPLeftInfo().setVisible(true);
            Core.nextTurn();
            handUp.setPlayer(Core.getActual());
            handUp.setPlayerCards();
            handUp.cardDraw();
            gameView.getjPUPInfo().setVisible(true);
            Core.nextTurn();
            handRight.setPlayer(Core.getActual());
            handRight.setPlayerCards();
            handRight.cardDraw();
            gameView.getjPRightInfo().setVisible(true);
        }
        
        //se devuelve turno a jugador original
        Core.setActual(act);
                
    }

    public static void updateTable(){
        GameViewController.getPanel().updateHand();
        GameViewController.updateDecks();
        GameViewController.setLabelPlayers();
        GameViewController.setTableColor();
        
        if(Core.getMax() == 1){
            handUp.setPlayerCards();
            handUp.cardDraw();
            jLUpCardNumber.setText(String.valueOf(Core.getPlayer(handUp.getPlayer()).size()));
        }else if(Core.getMax() == 2){
            handRight.setPlayerCards();
            handRight.cardDraw();
            jLRightCardNumber.setText(String.valueOf(Core.getPlayer(handRight.getPlayer()).size()));
            
            handLeft.setPlayerCards();
            handLeft.cardDraw();
            jLLeftCardNumber.setText(String.valueOf(Core.getPlayer(handLeft.getPlayer()).size()));
        }else if(Core.getMax() == 3){
            handRight.setPlayerCards();
            handRight.cardDraw();
            jLRightCardNumber.setText(String.valueOf(Core.getPlayer(handRight.getPlayer()).size()));
            
            handUp.setPlayerCards();
            handUp.cardDraw();
            jLUpCardNumber.setText(String.valueOf(Core.getPlayer(handUp.getPlayer()).size()));
            
            handLeft.setPlayerCards();
            handLeft.cardDraw();
            jLLeftCardNumber.setText(String.valueOf(Core.getPlayer(handLeft.getPlayer()).size()));
        }
        
        //visualizacion de turno oponentes
        if(handUp.getPlayer() == Core.getActual()){
            jLUpCheck.setVisible(true);
        }else{
            jLUpCheck.setVisible(false);
        }
        
        if(handLeft.getPlayer() == Core.getActual()){
            jLLeftCheck.setVisible(true);
        }else{
            jLLeftCheck.setVisible(false);
        }
        
        if(handRight.getPlayer() == Core.getActual()){
            jLRightCheck.setVisible(true);
        }else{
            jLRightCheck.setVisible(false);
        }
        
        updateDirection();
        
        gameView.validate();
        gameView.repaint();
    }
    
    public static void updateDirection(){
        if(dir != Core.getDirection()){
            dir = Core.getDirection();
            
            //seteo de label de direccion inicial
            ImageIcon imageIcon = new ImageIcon("resources/direction"+dir+".png");
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(350 , 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            gameView.getjLDirection().setIcon(new ImageIcon(newimg));
            
        }
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
//        for(int i=0; i <= Core.getMax() ; i++){
//            //se recorre cada jugador y se muestra cuantas cartas tiene
//            int size = Core.getPlayer(i).size();
//            text += "<b>Jugador "+i+":</b> "+size; 
//            text += "<br>";
//        }
        text += "<b>Draw: </b>"+Core.getDraw().size()+"<br>";
        text += "<b>Drop: </b>"+Core.getDrop().size()+"<br>";
        text += "</html>";
        gameView.getjLPlayers().setText(text);
    }
    
    public static void setTableColor(){
        JPanel colorpanel = gameView.getjPColor();
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 6;
        c.ipady = 6;
        
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
            text += "Izquierda";
        }else{
            text += "Derecha";
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
    }
}
