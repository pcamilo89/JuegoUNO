/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Camilo
 */
public class Utils {
    public static final int TRAMA_FLAG = 126;
    public static final int TRAMA_NULL = 0;
    public static final int TRAMA_INFO = 128;
    
    
    public static void printMSG(int c){
        char info = (char) c;
        System.out.print(info+"\t");
        System.out.print(c+"\t");
        if(c==Utils.TRAMA_FLAG){
            System.out.print("-FLAG-\n");
        }else
            System.out.print("\n");
    }
    
    /**
     * Metodo que convierte una cadena string que representa numero binario a numero decimal
     * @param data entrada en binario
     * @return salida en decimal
     */
    public static int binaryToInt(String data){
        return Integer.parseInt(data,2);
    }
    
    /**
     * Metodo que convierte un numero decimal en cadena string que representa numero binario
     * @param data entrada en decimal
     * @return salida en string binario
     */
    public static String IntToBinary(int data){
        String temp = Integer.toBinaryString(data);
        
        String fill="";
        if(temp.length()<8){
            for(int i=temp.length();i<8;i++){
                fill=fill+"0";
            }
        }
        fill=fill+temp;
        return fill;
    }
    
    public enum Color{
        Azul,
        Verde,
        Rojo,
        Amarillo,
        None
    }
    public enum Value{
        Cero,
        Uno,
        Dos,
        Tres,
        Cuatro,
        Cinco,
        Seis,
        Siete,
        Ocho,
        Nueve,
        PierdeTurno,
        CambiaSentido,
        MasDos,
        CambiaColor,
        MasCuatro,
        None
    }
    
}
