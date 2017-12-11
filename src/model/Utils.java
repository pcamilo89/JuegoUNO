/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.JPColorChooser;

/**
 * Clase donde se guardan funciones y variables generales y constantes
 * @author Camilo
 */
public class Utils {
    /**
     * Puerto serial por defecto
     */
    public static String comPort = "COM1"; 
    /**
     * Nombre de la aplicacion
     */
    public static final String APP_NAME = "Juego UNO";
    
    public static final int CONTROL_START_GAME = 1;
    public static final int CONTROL_STARTING_CARDS = 2;
    public static final int CONTROL_VICTORY = 3;
    public static final int CONTROL_CARD_HAND = 6;
    public static final int CONTROL_CARD_PLAY = 7;
    
    public static final int PHASE_START = 0;
    public static final int PHASE_INITIAL_CARDS = 1;
    public static final int PHASE_BOARD = 2;
    public static final int PHASE_GAME = 3;
    public static final int PHASE_VICTORY = 4;
    
    public static final String INFO_FILLER_5 = "10000";
    public static final String INFO_FILLER_8 = "10000000";
    
    public static final int TRAMA_FLAG = 126;
    public static final int TRAMA_NULL = 0;
    public static final int TRAMA_INFO = 128;
    
    public static final int BYTE_SIZE = 8;
    public static final int BUFFER_SPEED = 2400;
    public static final int SLEEP_TIME = 250;
    
    public static final float CARD_SCALE = 3;
    
    public static final int GAMEVIEW_WIDTH = 640;
    public static final int GAMEVIEW_HEIGHT = 480;
    
    public static final String COLOR_DIALOG = "Elije un Color:";
    
    /**
     * Metodo que imprime cada caracter recibido en el buffer y identifica las flags
     * @param c ultimo caracter representado como in
     */
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
     * @param size tamaño minimo deseado
     * @return salida en string binario
     */
    public static String intToBinary(int data,int size){
        String temp = Integer.toBinaryString(data);
        
        //PRUEBA PROBLEMA COLOR NONE

        return completeBinary(temp, size);
    }
    
    /**
     * Metodo que completa los ceros de un numero binario al tamaño indicado
     * @param data string de un numero binario
     * @param size tamaño del numero binario deseado
     * @return salida en string binario
     */
    public static String completeBinary(String data,int size){
        
        //System.out.println("Size: "+data.length());
        String fill="";
        if(data.length()<size){
            for(int i=data.length();i<size;i++){
                fill=fill+"0";
            }
        }
        fill=fill+data;
        return fill;
    }
    
    /**
     * convercion de byte sin signo a int
     * @param b byte a ser convertido
     * @return int con rango de 0-255
     */
    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }
    
    public enum Color{
        AZUL,
        VERDE,
        ROJO,
        AMARILLO,
        NONE
    }
    
    /**
     * Metodo para convertir de color a int
     * @param color color a ser convertido
     * @return int equivalente al color recibido
     */
    public static int colorToInt(Color color){
        int num=0;
        for (Color i : Color.values()) {
            if(i.equals(color)){
                //si el color es none se cambia
                if(i.equals(Color.NONE)){
                    System.out.println("CARTA SIN COLOR EN COLOR TO INT");
                    System.out.flush();
                    return 2; //retornando verde para cartas sin color
                }
                return num;
            }
            num++;
        }
        return -1;
    }
    
    /**
     * Metodo para convertir de int a color
     * @param x int a ser convertido
     * @return color equivalente al int recibido
     */
    public static Color intToColor(int x){
        int num=0;
        for (Color i : Color.values()) {
            if(num==x){
                return i;
            }
            num++;
        }
        return null;
    }
    
    public enum Value{
        CERO,
        UNO,
        DOS,
        TRES,
        CUATRO,
        CINCO,
        SEIS,
        SIETE,
        OCHO,
        NUEVE,
        PIERDE_TURNO,
        CAMBIA_SENTIDO,
        MAS_DOS,
        CAMBIA_COLOR,
        MAS_CUATRO,
        NONE
    }
    
        /**
         * Metodo para convertir de value a int
         * @param value valor recibido
         * @return int equivalente al value suministrado
         */
        public static int valueToInt(Value value){
        int num=0;
        for (Value i : Value.values()) {
            if(i.equals(value)){
                return num;
            }
            num++;
        }
        return -1;
    }
    
    /**
     * Metodo para convertir de int a value
     * @param x valor int recibido
     * @return value equivalente al int suministrado
     */
    public static Value intToValue(int x){
        int num=0;
        for (Value i : Value.values()) {
            if(num==x){
                return i;
            }
            num++;
        }
        return null;
    }
    
    /**
     * Metodo para obtener un int random a partir de una seed
     * @param tope numero maximo o tope de rango
     * @return int aleatorio
     */
    public static int getRandomint(int tope){
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(tope);
        
        return index;
    }
    
    public static boolean textDialog(String text, JFrame father){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(text);
        panel.add(label);
        String[] options = new String[]{"Yes"};
        int option = JOptionPane.showOptionDialog(father, panel, Utils.APP_NAME,
        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
        if(option == 0) // pressing OK button
        {
            return true;
        }
        return false;
    }
    
        public static int colorChooser(String text, JFrame father){
            JPColorChooser panel = new JPColorChooser();
            panel.getjLText().setText(text);
            
            panel.getjPBlue().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panel.setColor(Utils.colorToInt(Color.AZUL));
                    panel.getjLText().setText("Seleccionado "+Color.AZUL);
                }
            });
            
            panel.getjPGreen().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panel.setColor(Utils.colorToInt(Color.VERDE));
                    panel.getjLText().setText("Seleccionado "+Color.VERDE);
                }
            });
            
            panel.getjPRed().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panel.setColor(Utils.colorToInt(Color.ROJO));
                    panel.getjLText().setText("Seleccionado "+Color.ROJO);
                }
            });
            
            panel.getjPYellow().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    panel.setColor(Utils.colorToInt(Color.AMARILLO));
                    panel.getjLText().setText("Seleccionado "+Color.AMARILLO);
                }
            });
            
            String[] options = new String[]{"Yes"};
            int option = JOptionPane.showOptionDialog(father, panel, Utils.APP_NAME,
            JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]);
            if(option == 0) // pressing OK button
            {
                return panel.getColor();
            }
            return panel.getColor();
            
        }
}
