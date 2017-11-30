/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import model.Utils;

/**
 *
 * @author Camilo
 */
public class TestIntBinary {
   public static void main(String[] args){
       String data = "10000000";
       int number = Utils.binaryToInt(data);
       System.out.println(number);
       data = Utils.intToBinary(number,Utils.BYTE_SIZE);
       System.out.println(data);
       
       
       //byte b = Byte.parseByte("10000000");
       int value = 128;
       int num = Utils.unsignedToBytes((byte) value);
       System.out.println(num);
   } 
}
