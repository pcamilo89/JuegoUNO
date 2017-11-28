package model;

public class Trama {
    private int[] values = new int[4];

    public Trama(){
        values[0]=Utils.TRAMA_NULL;
        values[1]=Utils.TRAMA_NULL;
        values[2]=Utils.TRAMA_NULL;
        values[3]=Utils.TRAMA_NULL;
    }
    
    public Trama(int control, int information){
        values[0]=Utils.TRAMA_FLAG;
        values[1]=control;
        values[2]=information;
        values[3]=Utils.TRAMA_FLAG;
    }
    
    public int getControl(){
        return values[1];
    }
    
    public int getInformation(){
        return values[2];
    }
    
    public void fillTrama(int c){        
        for(int i=0;i<values.length-1;i++){
            values[i]=values[i+1];
        }
        values[3]=c;
        
    }
    
    public boolean checkTrama(){
        if(values[0]==Utils.TRAMA_FLAG&&values[1]!=Utils.TRAMA_FLAG&&values[2]!=Utils.TRAMA_FLAG&&values[3]==Utils.TRAMA_FLAG){
            return true;
        }
        return false;
    }
    
    public void printTrama(){
        for(int i=0;i<values.length;i++){
            System.out.print(values[i]+".");
        }
        System.out.print("\n");
    }

}
