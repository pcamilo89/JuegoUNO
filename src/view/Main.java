/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import model.Core;
import model.SerialComm;
import model.SerialReader;
import model.Utils;

/**
 * Clase principal donde se inicializa el juego
 * @author Camilo
 */
public class Main {
    public static SerialReader reader = null;
    
    /**
     * Metodo para inicializar el el puerto y coneccion asi como parametros iniciales
     */
    public static boolean startApp(){
        boolean status = false;
        try {
            if(SerialComm.getSerialPort() == null&&reader==null){
                //Iniciando conneccion serial
                SerialComm.connect(Utils.comPort);
                System.out.println("Serial Comm Iniciado en: "+Utils.comPort);
                
                //Iniciando Hilo de Entrada por Serial
                reader = new SerialReader(SerialComm.getIn());
                new Thread(reader).start();
                status = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //inicializacion del mazo de juego
        Core.getDraw().clearDeck();
        Core.getDraw().createDeck();
        Core.setPlayers();
        return status;
    }
    
    /**
     * Metodo para detener la coneccion y el hilo de escucha
     */
    public static boolean stopApp(){
        boolean status = false;
       if(SerialComm.getSerialPort() != null&&reader!=null){
           //Primero cerrar hilo de esucha
           reader.setLive(false);
           reader = null;
           
           //Luego se cierra puerto
           SerialComm.close();
           System.out.println("Serial Comm Finalizado");
           status = true;
       }
       return status;
    }
    
    /**
     * Metodo que establece el look por defecto de la aplicacion
     */
    public static void setLookAndFeel(){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("joxy.JoxyLookAndFeel".equals(info.getName())) {

                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;

                }
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo principal de ejecucion
     * @param args parametros de entrada
     */
    public static void main(String[] args){
        //Inicializacion de interfaz 
        setLookAndFeel();
        MainView mainView = new MainView();
        mainView.setVisible(true);
        
    }
}
