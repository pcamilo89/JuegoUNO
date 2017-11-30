package model;

/**
 * Clase para el manejo de la trama
 * @author Camilo
 */
public class Trama {
    private int[] values = new int[4];

    /**
     * Constructor de Trama
     */
    public Trama(){
        values[0]=Utils.TRAMA_NULL;
        values[1]=Utils.TRAMA_NULL;
        values[2]=Utils.TRAMA_NULL;
        values[3]=Utils.TRAMA_NULL;
    }
    /**
     * Constructor de trama que recibe segmento de control e informacion
     * @param control segmento o byte de control en int
     * @param information segmento o byte de informacion en int
     */
    public Trama(int control, int information){
        values[0]=Utils.TRAMA_FLAG;
        values[1]=control;
        values[2]=information;
        values[3]=Utils.TRAMA_FLAG;
    }
    
    /**
     * Metodo para obtener segmento de control
     * @return int que representa byte o segmento de control
     */
    public int getControl(){
        return values[1];
    }
    
    /**
     * Metodo para obtener segmento de informacion
     * @return int que representa byte o segmento de informacion
     */
    public int getInformation(){
        return values[2];
    }
    
    /**
     * Metodo para el llenado en serie de la trama
     * @param c int que representa byte o segmento que entra
     */
    public void fillTrama(int c){        
        for(int i=0;i<values.length-1;i++){
            values[i]=values[i+1];
        }
        values[3]=c;
        
    }
    
    /**
     * Metodo que chequea si la estructura de la trama es valida
     * @return boolean true or false
     */
    public boolean checkTrama(){
        if(values[0]==Utils.TRAMA_FLAG&&values[1]!=Utils.TRAMA_FLAG&&values[2]!=Utils.TRAMA_FLAG&&values[3]==Utils.TRAMA_FLAG){
            return true;
        }
        return false;
    }
    
    /**
     * Metodo para la impresion de la trama
     */
    public void printTrama(){
        for(int i=0;i<values.length;i++){
            System.out.print(values[i]+".");
        }
        System.out.print("\n");
    }

}
