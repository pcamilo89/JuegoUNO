/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Clase TestComm donde se realizo prueba solo envio y conversion de datos para enviar.
 * @author Camilo
 */
public class TestEnvio {
    
    public TestEnvio()
    {
        super();
    }
    
    @SuppressWarnings("empty-statement")
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(2400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                
                //(new Thread(new TwoWaySerialComm.SerialReader(in))).start();
                //(new Thread(new TwoWaySerialComm.SerialWriter(out))).start();
                
                int var;
                int num;
                
//                //ejemplo de bytearray
//                byte[] array = { 48, 48, 48, 48, 49, 48, 48, 49 };
//                out.write(array);
                
//                //salto de linea
//                var=10;
//                out.write(var);
//                
//                //ejemplo de un solo byte desde un int
//                var = 512;
//                out.write(var);
                
                //Ejemplo byte desde cadena de string y viceversa
                String ori = "01111110";
                
                //num = Integer.parseInt("7E",16);
                //num = 126;
                
                //Conversion de string binario a numero decimal
                num = Integer.parseInt(ori,2);
                System.out.println(num+" Decimal");
                
                //Conversion de decimal a numero hex
                System.out.println(Integer.toHexString(num)+" Hex");
                
                //Conversion de decimal a string binario
                String test = Integer.toBinaryString(num);
                System.out.println(test+" Binario SCI"); //Sin Ceros Izquierda

                //Completacion de binario a 8 bit
                String value="";
                if(test.length()<8){
                    for(int i=test.length();i<8;i++){
                        value=value+"0";
                    }
                }
                value=value+test;
                test=value;
                System.out.println(test+" Binario CCI"); //Con Ceros Izquierda 8bit
                
                out.write(num);
                
                //Cierre del puerto comm
                serialPort.close();
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    public static void main(String[] args){
        System.out.println("Prueba de puerto comm");
        
        //inicializacion de coneccion puerto COM2
        try
        {
            (new TestEnvio()).connect("COM2");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
