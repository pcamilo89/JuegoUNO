/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.MainViewController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para el manejo del protocolo tanto entrada como salida de datos
 * @author Camilo
 */
public class Protocol {
    static String control = null;

    static String from = null;
    static String to = null;
    static String instruction = null;

    static String info = null;
    static String direction = null;
    static String color = null;
    static String card =  null;
    static String mode = null;
    static String count = null;
    
    static Card newCard = null;
    
    
    /**
     * Metodo que maneja las tramas que entran por el canal de escucha
     * @param trama conformado por 4 bytes donde los dos centrales conforman la informacion deseada
     */
    public static void processTrama(Trama trama){
        
        try {
            //PARA TESTEAR
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trama.printTrama();
        
        //se convierte el segmento de control en string para subdividirlo
        control= Utils.intToBinary(trama.getControl(),Utils.BYTE_SIZE);

        from = control.substring(0, 2);
        to = control.substring(2, 4);
        instruction = control.substring(4, 8);
        
        //se convierte el segmento de informacion en string para subdividirlo
        info = Utils.intToBinary(trama.getInformation(),Utils.BYTE_SIZE);
        System.out.println("INFOE:"+info);
        
        direction = null;
        color = null;
        card =  null;
        mode = null;
        count = null;
        newCard = null;
        
        //System.out.println(control+"-"+info);
        
        //Muestra en la interfaz inicial el turno
        MainViewController.setTurnLabel("Turno: "+Utils.binaryToInt(to));
        
        //si se reciben instrucciones 6 o 7 de manejo de cartas
        if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_HAND||Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_PLAY){
            direction = info.substring(1,2);
            color = info.substring(2, 4);
            card = info.substring(4, 8);
            System.out.println(from+" "+to+" "+instruction+" "+direction+" "+color+" "+card);
            
                        
            //instruccion de carta a la mano
            if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_HAND){
                //si recibo carta a la mano y no soy yo
                if(Utils.binaryToInt(from)!=Core.getLocal()){
                    Core.setActual(Utils.binaryToInt(from));
                    
                    newCard = null;
                    while(newCard==null){
                        newCard = Core.getCard(Utils.intToColor(Utils.binaryToInt(color)),Utils.intToValue(Utils.binaryToInt(card)));
                    }                    
                    Core.getPlayer(Utils.binaryToInt(from)).addCard(newCard);
                    //y se retransmite la trama
                    System.out.println("INFOS:"+info);
                    SerialComm.sendTrama(trama);
                }else{
                    //si recibo carta a la mano y soy yo
                    //y se esta en fase de cartas iniciales
                    if(Core.getPhase()==Utils.PHASE_INITIAL_CARDS){
                        if(Core.getCounter()<7){
                            //se obtiene la carta n a la mano
                            Core.setActual(Core.getLocal());
                            
                            newCard = null;
                            while(newCard==null){
                                newCard = Core.getRandomCard();
                                
                            }
                            
                            //Primero se pasa la trama
                            //Luego se chequea si color valido para cartas sin color
                            //Se agrega a la mano

                            //se construye la trama a enviar
                            from = Utils.intToBinary(Core.getLocal(), 2);
                            to = Utils.intToBinary(Core.getLocal(), 2);
                            instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                            control = from+to+instruction;

                            direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                            color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                            card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                            info = direction+color+card;
                            System.out.println("INFOS:"+info);
                            
                            trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                            SerialComm.sendTrama(trama);
                            
                            //se chequea la carta y su color y se agrega
                            newCard.checkCard();
                            Core.getPlayer(Core.getLocal()).addCard(newCard);
                            Core.setCounter(Core.getCounter()+1);
                        
                        }else{
                            //en caso de terminar de sacar mis 7 cartas se cambia de fase
                            Core.setPhase(Utils.PHASE_BOARD);
                            MainViewController.setInfoLabel("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                            System.out.println("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                            
                            //se le da turno al siguiente de sacar sus 7 cartas
                            Core.nextTurn();
                            to = Utils.intToBinary(Core.getActual(), 2);
                            instruction = Utils.intToBinary(Utils.CONTROL_STARTING_CARDS, 4);
                            
                            control = from+to+instruction;
                            info = Utils.INFO_FILLER_8;
                            System.out.println("INFOS:"+info);
                            
                            trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                            SerialComm.sendTrama(trama);
                            
                        }
                    }else if(Core.getPhase()==Utils.PHASE_BOARD&&Core.getLocal()==0){
                        //y se esta en fase de preparacion de mesa y soy jugador inicial o 0
                        Core.setActual(Core.getLocal());
                                                
                        //se toma la ultima carta en la mano y se envia primer mensaje de carta a la mesa
                        newCard = null;
                        while(newCard==null){
                            newCard = Core.getPlayer(Core.getLocal()).showLastCard();
                        }
                        
                        //se elije jugador al azar,este jugador sera realmente el primero en jugar
                        //todos los jugadores tienen 7 cartas en este momento y una carta esta siendo colocada en la mesa
                        //se aprovecha la misma instruccion para colocar la carta y pasar el turno
                        from = Utils.intToBinary(Core.getLocal(), 2);
                        to = Utils.intToBinary(Utils.getRandomint(Core.getMax()+1), 2);
                        //IMPRESION DE PRIMER JUGADOR
                        System.out.println("INICIA:"+to);
                        
                        instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                        control = from+to+instruction;
                        
                        direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                        color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                        card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                        info = direction+color+card;
                        System.out.println("INFOS:"+info);
                        
                        trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                        SerialComm.sendTrama(trama);
                        
                    }
                }
            }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_PLAY){
                //si se recibe una instruccion de carta a la mesa
                if(Core.getPhase()==Utils.PHASE_BOARD){
                    //en caso de no estar en fase de juego se inicia
                    Core.setPhase(Utils.PHASE_GAME);
                    MainViewController.setInfoLabel("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                    System.out.println("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                    //en este punto se debe cambiar de ventana al tablero de juego
                    
                    //CAMBIO A TABLERO DE JUEGO
                    
                }
                
                //se asigna el turno al destino
                Core.setActual(Utils.binaryToInt(to));
                
                //si yo no envie el mensaje
                if(Core.getLocal()!=Utils.binaryToInt(from)){
                    //se retransmite el mensaje y se coloca la carta en la mesa
                    //System.out.println("CARTA A LA MESA DE: "+from);
                    
                    newCard = null;
                    while(newCard==null){
                        newCard = Core.getPlayer(Utils.binaryToInt(from)).getCard(Utils.intToColor(Utils.binaryToInt(color)),Utils.intToValue(Utils.binaryToInt(card)));
                    }                    
                    Core.getDrop().addCard(newCard);
                    
                    //si es mi turno
                    if(Core.getActual()==Core.getLocal()){
                        //Es mi turno debo activar la interfaz
                        System.out.println("ES MI TURNO SOY:"+to);
                        
                        //CODIGO DE ACTIVACION DE INTERFAZ
                        //TURNO DE JUGADOR EN FASE DE JUEGO
                        
                    }
                    SerialComm.sendTrama(trama);
                    
                }else{
                    //si yo envie el mensaje
                    //no se retransmite solo se coloca carta en la mesa
                    
                    newCard = null;
                    while(newCard==null){
                        newCard = Core.getPlayer(Utils.binaryToInt(from)).getCard(Utils.intToColor(Utils.binaryToInt(color)),Utils.intToValue(Utils.binaryToInt(card)));
                    }
                    
                    System.out.println("YO ENVIE EL MENSAJE Y PUSE LA CARTA EN LA MESA");
                    System.out.flush();
                    Core.getDrop().addCard(newCard);
                    //if(newCard!=null)
                    
                    
                    //si es mi turno (solo sucede si soy JUGADOR INICIAL
                    if(Core.getActual()==Core.getLocal()){
                        //Es mi turno debo activar la interfaz
                        System.out.println("ES MI TURNO Y SOY INICIAL");
                        
                        //CODIGO DE ACTIVACION DE INTERFAZ
                        //TURNO DE JUGADOR EN FASE DE JUEGO
                        
                    }
                }
            }
            
            //EN CASO DE CARTA A LA MESA O CARTA A LA MANO SE DEBE ACTUALIZAR LA INTERFAZ INICIAL
            String tableInfo = "J0:"+Core.getPlayer(0).size()+" J1:"+Core.getPlayer(1).size()+" J2:"+Core.getPlayer(2).size()+" J3:"+Core.getPlayer(3).size()
                    +" Draw:"+Core.getDraw().size()+" Drop:"+Core.getDrop().size();
            MainViewController.setCardsLabel(tableInfo);
            //SI YA SE ESTA EN FASE DE JUEGO
            if(Core.getPhase()>=Utils.PHASE_GAME){
                //Mostrar la mesa al pasar a fase juego
                Core.printTable();
            }
            
        }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_START_GAME){
            //inicio de partida que se define por instruccion 0001 y mode
            mode = info.substring(5,6);
            count = info.substring(6,8);
            System.out.println(from+" "+to+" "+instruction+" "+mode+" "+count);
            
            Core.setPhase(Utils.PHASE_START);
            //si el modo es 0
            if(Utils.binaryToInt(mode)==0){
                //si no soy el origen o jugador 0
                if(Core.getLocal()!=0){
                    //desabilitado de botones
                    MainViewController.disableButtons();
                    //sumo uno a contador seteando identificador local
                    Core.setLocal(Utils.binaryToInt(count)+1);
                    System.out.println("Local: "+Core.getLocal());
                    MainViewController.setPlayerLabel("Jugador "+Core.getLocal());
                    MainViewController.setInfoLabel("Local: "+Core.getLocal());
                    
                    //construccion de trama para siguiente jugador
                    count = Utils.intToBinary(Core.getLocal(), 2);
                    control = from+to+instruction;
                    
                    info=Utils.INFO_FILLER_5+mode+count;
                    System.out.println("INFOS:"+info);
                    
                    //envio de trama al siguiente jugador
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                }else{
                    //si soy el origen paso a modo broadcast de info
                    control = from+to+instruction;
                    mode = Utils.intToBinary(1, 1);
                    info=Utils.INFO_FILLER_5+mode+count;
                    System.out.println("INFOS:"+info);
                    
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                    
                    //Seteo total de jugadores
                    Core.setMax(Utils.binaryToInt(count));
                    MainViewController.setInfoLabel("Local: "+Core.getLocal()+" Max:"+Core.getMax());
                }                
            }else{
                //si el modo es 1
                if(Core.getLocal()!=0){
                    Core.setPhase(Utils.PHASE_INITIAL_CARDS);
                    //si no soy el origen o jugador 0
                    //Seteo total de jugadores
                    Core.setMax(Utils.binaryToInt(count));
                    System.out.println("INFOS:"+info);
                    
                    MainViewController.setInfoLabel("Local: "+Core.getLocal()+" Max:"+Core.getMax());
                    SerialComm.sendTrama(trama);
                }else{
                    //si soy el origen y es modo 1
                    //comienzan fase de cartas iniciales;
                    Core.setPhase(Utils.PHASE_INITIAL_CARDS);
                                        
                    //se obtiene la primera carta a la mano
                    newCard = null;
                    while(newCard==null){
                        newCard = Core.getRandomCard();
                    }
                                        
                    //se construye la trama a enviar
                    from = Utils.intToBinary(Core.getLocal(), 2);
                    to = Utils.intToBinary(Core.getLocal(), 2);
                    instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                    control = from+to+instruction;
                    
                    direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                    color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                    card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                    info = direction+color+card;
                    System.out.println("INFOS:"+info);
                    
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                    
                    //se chequea color de la carta y se agrega a la mano
                    newCard.checkCard();
                    Core.getPlayer(Core.getLocal()).addCard(newCard);
                    Core.setCounter(Core.getCounter()+1);
                }
            }
        }else{
            System.out.println(from+" "+to+" "+instruction);
            
            //si recibo mensaje de cartas iniciales y soy jugador distinto de cero
            if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS&&Core.getLocal()!=0){
                Core.setActual(Core.getLocal());
                //comienzan fase de cartas iniciales;

                //se obtiene la primera carta a la mano
                newCard = null;
                while(newCard==null){
                    newCard = Core.getRandomCard();
                }
                
                //se construye la trama a enviar
                from = Utils.intToBinary(Core.getLocal(), 2);
                to = Utils.intToBinary(Core.getLocal(), 2);
                instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                control = from+to+instruction;

                direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                info = direction+color+card;
                System.out.println("INFOS:"+info);

                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                SerialComm.sendTrama(trama);
                
                //se chequea color de la carta y se agrega a la mano
                newCard.checkCard();
                Core.getPlayer(Core.getLocal()).addCard(newCard);
                Core.setCounter(Core.getCounter()+1);
                
            }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS&&Core.getLocal()==0){
                //recibo mensaje de cartas iniciales y soy jugador inicial o 0
                Core.setActual(Core.getLocal());
         
                //se obtiene en mano la carta que va a la mesa
                newCard = null;
                while(newCard==null){
                    newCard = Core.getRandomCard();
                }
                
                //se construye la trama a enviar
                from = Utils.intToBinary(Core.getLocal(), 2);
                to = Utils.intToBinary(Core.getLocal(), 2);
                instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                control = from+to+instruction;

                direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                info = direction+color+card;
                System.out.println("INFOS:"+info);

                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                SerialComm.sendTrama(trama);
                
                //se chequea color de la carta y se agrega a la mano
                newCard.checkCard();
                Core.getPlayer(Core.getLocal()).addCard(newCard);
                
            }
        }
    }
    
    /**
     * Metodo inicial de partida ejecutado por jugador inicial o cero
     */
    public static void startGame(){
        //construccion de segmento control
        Core.setLocal(0);
        MainViewController.setPlayerLabel("Jugador "+Core.getLocal());
        MainViewController.setInfoLabel("Local: "+Core.getLocal());
        System.out.println("Local: "+Core.getLocal());
        
        from = Utils.intToBinary(Core.getLocal(), 2);
        to = from;
        instruction = Utils.intToBinary(Utils.CONTROL_START_GAME, 4);
        control = from+to+instruction;

        //construccion de segmento informacion
        info = Utils.INFO_FILLER_8;
        System.out.println("INFOS:"+info);
        
        //envio de la primera trama
        Trama trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
        SerialComm.sendTrama(trama);
    }
}
