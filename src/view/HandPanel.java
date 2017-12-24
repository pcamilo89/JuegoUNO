/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Core;
import model.Utils;

/**
 *
 * @author Camilo
 */
public class HandPanel extends JPanel{
    private ArrayList<CardView> list;
    private int ori = 1;
    private int player;
    private int cwidth;
    private int cheight;
    
    JLabel label;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
    
    public void setPlayerCards(){
        setCardNumber(Core.getPlayer(player).size());
    }

    public HandPanel(int ori) {
        super();
        this.setLayout(null);
        this.list = new ArrayList();
        this.ori = ori;
        this.setOpaque(false);
        
        CardView card = new CardView();
        card.setScale(Utils.CARD_SCALE+1);
        cwidth = card.getWidth();
        cheight = card.getHeight();
                
        label = new JLabel("x");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
    }

    public void setCardNumber(int num){
        if(num != list.size()){
            list.clear();
            for(int i = 0; i < num; i++){
                CardView card = new CardView(this);
                card.setScale(Utils.CARD_SCALE+1);
                list.add(card);
            }
            label.setText(String.valueOf(num));
        }
    }

    public void cardDraw(){
        this.removeAll();
        this.repaint();
        Iterator iterator = list.listIterator();

        int coordx = 5;
        int coordy = 5;

        int center = 0;
        int csize = 0;
        int area = 0;

        if(ori == 0){
            center = (int) (this.getWidth()/2);
            csize = cwidth;
            area = this.getWidth();
        }else if(ori == 1){
            center = (int) (this.getHeight()/2);
            csize = cheight;
            area = this.getHeight();
        }

        //logica para dibujar numero de cartas
//        JPanel temp = new JPanel();
//        temp.setLayout(null);
//        int psize = 20;
//        temp.setBorder(BorderFactory.createRaisedBevelBorder());
//        this.add(temp);
//        if(ori == 0){
//            temp.setBounds(center - (psize/2), coordy+5, psize, psize);
//        }else if(ori == 1){
//            temp.setBounds(coordx+5, center - (psize/2), psize, psize);
//        }
//        temp.add(label);
//        label.setBounds(0, 0, temp.getWidth(), temp.getHeight());
        
        //logica para dibujar cartas
        int div = 2;
        int carea = (csize/div);

        int tarea = csize + (carea*(list.size()-1));

        while(tarea > area && tarea > csize){
            div++;
            carea = (csize/div);
            tarea = csize + (carea*(list.size()-1));                
        }
                
        //System.out.println("div: "+div+" size: "+list.size());
        tarea /= 2;

        int initpos = center - (csize/2);
        if(list.size() > 1){
            initpos = center - tarea;
        }

        if(ori == 0){
            coordx = initpos;
        }else if(ori == 1){
            coordy = initpos;
        }

        while(iterator.hasNext()){
            CardView card = (CardView) iterator.next();
            this.add(card);
            card.setBounds(coordx, coordy, card.getWidth(), card.getHeight());

            if(ori == 0){
                coordx += carea;
            }else if(ori == 1){
                coordy += carea;
            }

        }
        this.validate();

    }
}
