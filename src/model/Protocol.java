/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.MainViewController;

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
     * Metodo que maneja las tramas que entran por el canal de esucha
     * @param trama conformado por 4 bytes donde los dos centrales conforman la informacion deseada
     */
    public static void processTrama(Trama trama){
        
        //System.out.println("Recepcion: ");
        trama.printTrama();
        
        //se convierte el segmento de control en string para subdividirlo
        control= Utils.intToBinary(trama.getControl(),Utils.BYTE_SIZE);

        from = control.substring(0, 2);
        to = control.substring(2, 4);
        instruction = control.substring(4, 8);
        
        //se convierte el segmento de informacion en string para subdividirlo
        info = Utils.intToBinary(trama.getInformation(),Utils.BYTE_SIZE);
        direction = null;
        color = null;
        card =  null;
        mode = null;
        count = null;
        newCard = null;
        
        System.out.println(control+"-"+info);
        
        //si se reciben instrucciones 6 o 7 de manejo de cartas
        if(Utils.binaryToInt(instruction)==6||Utils.binaryToInt(instruction)==7){
            direction = info.substring(1,2);
            color = info.substring(2, 4);
            card = info.substring(4, 8);
            System.out.println(from+" "+to+" "+instruction+" "+direction+" "+color+" "+card);
            
            //instruccion de carta a la mano
            if(Utils.binaryToInt(instruction)==6){
                //si recibo carta a la mano y no soy yo
                if(Utils.binaryToInt(from)!=Core.getLocal()){
                    Core.setActual(Utils.binaryToInt(to));
                    newCard = Core.getCard(Utils.intToColor(Utils.binaryToInt(color)),Utils.intToValue(Utils.binaryToInt(card)));
                    Core.getPlayer(Utils.binaryToInt(from)).addCard(newCard);
                    SerialComm.sendTrama(trama);
                }else{
                    //si recibo carta a la mano y soy yo
                    //y se esta en fase de cartas iniciales
                    if(Core.getPhase()==Utils.PHASE_INITIAL_CARDS){
                        if(Core.getCounter()<7){
                            //se obtiene la carta n a la mano
                            Core.setActual(Core.getLocal());
                            newCard = Core.getRandomCard();
                            Core.getPlayer(Core.getLocal()).addCard(newCard);
                            Core.setCounter(Core.getCounter()+1);

                            //se construye la trama a enviar
                            from = Utils.intToBinary(Core.getLocal(), 2);
                            to = Utils.intToBinary(Core.getLocal(), 2);
                            instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                            control = to+from+instruction;

                            direction = Utils.intToBinary(Core.getDirection()+2, 2);
                            color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                            card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                            info = direction+color+card;

                            trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                            SerialComm.sendTrama(trama);
                        
                        }else if(Core.getCounter()==7){
                            //en caso de terminar de sacar mis 7 cartas se cambia de fase
                            Core.setPhase(Utils.PHASE_BOARD);
                            MainViewController.setInfoLabel("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                            System.out.println("Phase: "+Core.getPhase()+" Cards: "+Core.getPlayer(Core.getLocal()).size());
                            
                            //se le da turno al siguiente de sacar sus 7 cartas
                            Core.nextTurn();
                            to = Utils.intToBinary(Core.getActual(), 2);
                            instruction = Utils.intToBinary(Utils.CONTROL_STARTING_CARDS, 4);
                            
                            control = to+from+instruction;
                            info = Utils.INFO_FILLER_8;
                            
                            trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                            SerialComm.sendTrama(trama);
                            
                        }
                    }else if(Core.getPhase()==Utils.PHASE_BOARD&&Core.getLocal()==0){
                        //y se esta en fase de preparacion de mesa y soy jugador inicial o 0
                        Core.setActual(Core.getLocal());
                                                
                        //se toma la ultima carta en la mano y se notifica para poner en la mesa incluyendo a actual
                        newCard = Core.getPlayer(Core.getLocal()).getLastCard();
                        //System.out.println(newCard.toString());
                        //Core.getDrop().addCard(newCard);
                        
                        //se pasa a la siguiente fase que es fase de juego y se elige un jugador al azar
                        
                        //este jugador sera realmente el primero en jugar
                        //todos los jugadores tienen 7 cartas en este momento y una carta esta siendo colocada en la mesa
                        //se aprovecha la misma instruccion para colocar la carta y pasar el turno
                        from = Utils.intToBinary(Core.getLocal(), 2);
                        to = Utils.intToBinary(Utils.getRandomint(Core.getMax()+1), 2);
                        instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                        control = from+to+instruction;
                        
                        direction = Utils.intToBinary(Core.getDirection()+2, 2);
                        color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                        card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                        info = direction+color+card;
                        
                        trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                        SerialComm.sendTrama(trama);
                        
                    }
                }
            }else if(Utils.binaryToInt(instruction)==7){
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
                Core.setDirection(Utils.binaryToInt(direction));
                                
                //revisar primero si soy quien puso la carta para colocarla en caso contrario
                if(Core.getLocal()==Utils.binaryToInt(from)){
                    //recibo el mensaje y como soy yo mismo no se retransmite
                    if(Utils.binaryToInt(from)==Utils.binaryToInt(to)){
                        //solamente en el caso que yo toque al azar jugar primero
                        //siendo jugador inicial o 0
                        //tengo el turno en este momento
                        //se toma la ultima carta en la mano y se pone en la mesa
                        newCard = Core.getPlayer(Core.getLocal()).getLastCard();
                        //System.out.println(newCard.toString());
                        Core.getDrop().addCard(newCard);
                        
                        
                        //ASIGNACION DE TURNO
                    }
                }else{
                    //si no soy yo quedan dos casos y ambos deben retransmitir
                    
                    if(Core.getActual()==Core.getLocal()){
                        //si yo soy el del turno actual debo colocar la carta y
                                                
                        //se toma la carta jugada y se pone en la mesa
                        newCard = Core.getPlayer(Utils.binaryToInt(from)).getCard(Utils.intToColor(Utils.binaryToInt(color)), Utils.intToValue(Utils.binaryToInt(card)));
                        //System.out.println(newCard.toString());
                        Core.getDrop().addCard(newCard);
                        //e manda igual la trama
                        SerialComm.sendTrama(trama);
                        //luego tengo el turno
                        
                        //ASIGNACION DE TURNO
                        
                    }else{
                        //si yo no soy el del turno actual solo debo colocar la carta
                        //y pasar el mensaje igual como se recibio
                        Core.setDirection(Utils.binaryToInt(direction));
                        
                        //se toma la carta jugada y se pone en la mesa
                        newCard = Core.getPlayer(Utils.binaryToInt(from)).getCard(Utils.intToColor(Utils.binaryToInt(color)), Utils.intToValue(Utils.binaryToInt(card)));
                        //System.out.println(newCard.toString());
                        Core.getDrop().addCard(newCard);
                        //e manda igual la trama
                        SerialComm.sendTrama(trama);                        
                    }
                }           
            }
         }else if(Utils.binaryToInt(instruction)==1){
            //inicio de partida que se define por instruccion 0001 y mode
            mode = info.substring(5,6);
            count = info.substring(6,8);
            System.out.println(from+" "+to+" "+instruction+" "+mode+" "+count);
            
            //si el modo es 0
            if(Utils.binaryToInt(mode)==0){
                //si no soy el origen o jugador 0
                if(Core.getLocal()!=0){
                    //desabilitado de botones
                    MainViewController.disableButtons();
                    //sumo uno a contador seteando identificador local
                    Core.setLocal(Utils.binaryToInt(count)+1);
                    System.out.println("Local: "+Core.getLocal());
                    MainViewController.setInfoLabel("Local: "+Core.getLocal());
                    
                    //construccion de trama para siguiente jugador
                    count = Utils.intToBinary(Core.getLocal(), 2);
                    control = to+from+instruction;
                    
                    info=Utils.INFO_FILLER_5+mode+count;
                    //envio de trama al siguiente jugador
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                }else{
                    //si soy el origen paso a modo broadcast de info
                    control = to+from+instruction;
                    mode = Utils.intToBinary(1, 1);
                    info=Utils.INFO_FILLER_5+mode+count;
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                    
                    //Seteo total de jugadores
                    Core.setMax(Utils.binaryToInt(count));
                    MainViewController.setInfoLabel("Local: "+Core.getLocal()+" Max:"+Core.getMax());
                }                
            }else{
                //si el modo es 1
                if(Core.getLocal()!=0){
                    //si no soy el origen o jugador 0
                    //Seteo total de jugadores
                    Core.setMax(Utils.binaryToInt(count));
                    MainViewController.setInfoLabel("Local: "+Core.getLocal()+" Max:"+Core.getMax());
                    SerialComm.sendTrama(trama);
                }else{
                    //si soy el origen y es modo 1
                    //comienzan fase de cartas iniciales;
                    Core.setPhase(Utils.PHASE_INITIAL_CARDS);
                                        
                    //se obtiene la primera carta a la mano
                    newCard = Core.getRandomCard();
                    Core.getPlayer(Core.getLocal()).addCard(newCard);
                    Core.setCounter(Core.getCounter()+1);
                    
                    //se construye la trama a enviar
                    from = Utils.intToBinary(Core.getLocal(), 2);
                    to = Utils.intToBinary(Core.getLocal(), 2);
                    instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                    control = to+from+instruction;
                    
                    direction = Utils.intToBinary(Core.getDirection()+2, 2);
                    color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                    card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                    info = direction+color+card;
                    
                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                }
            }
        }else{
            System.out.println(from+" "+to+" "+instruction);
            
            //si recibo mensaje de cartas iniciales y soy jugador distinto de cero
            if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS&&Core.getLocal()!=0){
                Core.setActual(Core.getLocal());
                //comienzan fase de cartas iniciales;
                Core.setPhase(Utils.PHASE_INITIAL_CARDS);

                //se obtiene la primera carta a la mano
                newCard = Core.getRandomCard();
                Core.getPlayer(Core.getLocal()).addCard(newCard);
                Core.setCounter(Core.getCounter()+1);

                //se construye la trama a enviar
                from = Utils.intToBinary(Core.getLocal(), 2);
                to = Utils.intToBinary(Core.getLocal(), 2);
                instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                control = to+from+instruction;

                direction = Utils.intToBinary(Core.getDirection()+2, 2);
                color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                info = direction+color+card;

                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                SerialComm.sendTrama(trama);
            }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS&&Core.getLocal()==0){
                //recibo mensaje de cartas iniciales y soy jugador inicial o 0
                Core.setActual(Core.getLocal());
                //se obtiene en mano la carta que va a la mesa
                newCard = Core.getRandomCard();
                Core.getPlayer(Core.getLocal()).addCard(newCard);
                
                //System.out.println(newCard.toString());
                
                //se construye la trama a enviar
                from = Utils.intToBinary(Core.getLocal(), 2);
                to = Utils.intToBinary(Core.getLocal(), 2);
                instruction = Utils.intToBinary(Utils.CONTROL_CARD_HAND,4);
                control = to+from+instruction;

                direction = Utils.intToBinary(Core.getDirection()+2, 2);
                color = Utils.intToBinary(Utils.colorToInt(newCard.getColor()), 2);
                card = Utils.intToBinary(Utils.valueToInt(newCard.getValue()), 4);
                info = direction+color+card;

                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                SerialComm.sendTrama(trama);
            }
        }
    }
    
    /**
     * Metodo inicial de partida ejecutado por jugador inicial o cero
     */
    public static void startGame(){
        //construccion de segmento control
        Core.setLocal(0);
        MainViewController.setInfoLabel("Local: "+Core.getLocal());
        System.out.println("Local: "+Core.getLocal());
        
        from = Utils.intToBinary(Core.getLocal(), 2);
        to = from;
        instruction = Utils.intToBinary(Utils.CONTROL_START_GAME, 4);
        control = from+to+instruction;

        //construccion de segmento informacion
        info = "10000000";
        
        
        Trama trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
        SerialComm.sendTrama(trama);
    }
}
