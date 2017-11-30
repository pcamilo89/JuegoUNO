/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;
    
import model.Core;
import model.Utils;

public class TestTurns {

    public static void main(String[] args) throws InterruptedException{
        int x = 0;
        while(true){
            x++;
            Thread.sleep(2000);
            System.out.println("Turno: "+Core.getActual()+" "+Utils.intToBinary(Core.getActual(), 2));
            
            if(Core.isLocalTurn()){
                System.out.println("Turno Local.");
            }
            if(x%10==0){
                Core.changeDirection();
                System.out.println("Cambio de Sentido.");
            }
            
            Core.nextTurn();
        }
                
    }
}
