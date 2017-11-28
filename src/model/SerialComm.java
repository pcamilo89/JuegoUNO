package model;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private static SerialPort serialPort;

    public static InputStream getIn() {
        return in;
    }

    public static void setIn(InputStream in) {
        SerialComm.in = in;
    }

    public static OutputStream getOut() {
        return out;
    }

    public static void setOut(OutputStream out) {
        SerialComm.out = out;
    }

    public static SerialPort getSerialPort() {
        return serialPort;
    }

    public static void setSerialPort(SerialPort serialPort) {
        SerialComm.serialPort = serialPort;
    }
    
    /**
     * Metodo de Conneccion por puerto serial que recibe el nombre del puerto a conectar.
     * @param portName String con el nombre del puerto
     * @throws Exception 
     */
    static void connect ( String portName ) throws Exception
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
                serialPort.setSerialPortParams(2400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
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
    static void close(){
        serialPort.close();
    }
    
    /**
     * Metodo para enviar un byte de informacion.
     * @param b byte de informacion a ser enviado
     * @throws IOException 
     */
    static void sendByte(byte b) throws IOException{
        out.write(b);
    }
    
    /**
     * Metodo de envio de trama
     * @param control segmento de control
     * @param information segmento de informacion
     * @throws IOException 
     */
    static void sendTrama(Trama trama) throws IOException{
        if(trama.checkTrama()){
            byte[] array = {(byte) Utils.TRAMA_FLAG,(byte) trama.getControl(), (byte) trama.getInformation(), (byte) Utils.TRAMA_FLAG};
            out.write(array);
        }
        
    }
}
