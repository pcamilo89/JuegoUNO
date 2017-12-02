/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import model.Core;

/**
 *
 * @author Camilo
 */
public class TestTable {
    public static void main(String[] args){
        Core.setPlayers();
        Core.getDraw().createDeck();
        
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        Core.getPlayer(0).addCard(Core.getRandomCard());
        System.out.println(Core.getPlayer(0).size());
        
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        Core.getPlayer(1).addCard(Core.getRandomCard());
        System.out.println(Core.getPlayer(1).size());
        Core.getDrop().addCard(Core.getPlayer(0).getLastCard());
        
        Core.printTable();
        System.out.println(Core.getDrop().size());
    }
}
