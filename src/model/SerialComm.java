package model;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que maneja Puerto Serial y su comunicacion.
 * @author Camilo
 */
public class SerialComm {
    //Canal de entrada RX
    private static InputStream in;
    //Canal de salida TX
    private static OutputStream out;
    //Puerto serial
    private static SerialPort serialPort = null;

    /**
     * Metodo para obtener stream de entrada
     * @return stream de entrada
     */
    public static InputStream getIn() {
        return in;
    }

    /**
     * Metodo para asignar stream de entrada
     * @param in stream de entrada
     */
    public static void setIn(InputStream in) {
        SerialComm.in = in;
    }

    /**
     * Metodo para obtener stream de salida
     * @return stream de salida
     */
    public static OutputStream getOut() {
        return out;
    }

    /**
     * Metodo para asignar stream de salida
     * @param out stream de salida
     */
    public static void setOut(OutputStream out) {
        SerialComm.out = out;
    }

    /**
     * Metodo para obtener puerto serial
     * @return puerto serial
     */
    public static SerialPort getSerialPort() {
        return serialPort;
    }

    /**
     * Metodo para asignar puerto serial
     * @param serialPort puerto serial
     */
    public static void setSerialPort(SerialPort serialPort) {
        SerialComm.serialPort = serialPort;
    }
    
    /**
     * Metodo de Conneccion por puerto serial que recibe el nombre del puerto a conectar.
     * @param portName String con el nombre del puerto
     * @throws Exception excepcion en caso de coneccion fallida
     */
    public static void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(portName, 2000);
            
            if ( commPort instanceof SerialPort )
            {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(Utils.BUFFER_SPEED,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                
                //Segmento donde originalmente se inician hilos de entrada y salida en el ejemplo de la libreria.
                //(new Thread(new TwoWaySerialComm.SerialReader(in))).start();
                //(new Thread(new TwoWaySerialComm.SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /**
     * Metodo para cerrar la conneccion del puerto serial.
     */
    public static void close(){
        serialPort.close();
        serialPort = null;
    }
    
    /**
     * Metodo para enviar un byte de informacion.
     * @param b byte de informacion a ser enviado 
     */
    public static void sendByte(byte b){
        try {
            out.write(b);
        } catch (IOException ex) {
            Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo de envio de trama
     * @param trama trama a enviar
     */
    public static void sendTrama(Trama trama){
        if(trama.checkTrama()){
            //System.out.println("Envio: ");
            //trama.printTrama();
            byte[] array = {(byte) Utils.TRAMA_FLAG,(byte) trama.getControl(), (byte) trama.getInformation(), (byte) Utils.TRAMA_FLAG};
            try {
                out.write(array);
            } catch (IOException ex) {
                Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
