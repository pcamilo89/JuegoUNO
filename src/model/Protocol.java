/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.GameViewController;
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
            Thread.sleep(Utils.SLEEP_TIME);
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
        
        //se asigna el turno al destino
        Core.setActual(Utils.binaryToInt(to));

        
        //si se reciben instrucciones 6 o 7 de manejo de cartas
        if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_HAND||Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_PLAY){
            direction = info.substring(1,2);
            color = info.substring(2, 4);
            card = info.substring(4, 8);
            System.out.println(from+" "+to+" "+instruction+" "+direction+" "+color+" "+card);
            
                                   
            //instruccion de carta a la mano
            if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_HAND){
                
                if(Core.getPhase()==Utils.PHASE_GAME){
                    //si estoy en fase de juego actualizo la mesa
                    GameViewController.updateTable();
                }
                //si recibo carta a la mano y no soy yo
                if(Utils.binaryToInt(from)!=Core.getLocal()){
                    //Core.setActual(Utils.binaryToInt(from));
                    
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
                            //Core.setActual(Core.getLocal());
                            
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
                            MainViewController.setInfoLabel("Fase: "+Core.getPhase()+" Cartas: "+Core.getPlayer(Core.getLocal()).size());
                            System.out.println("Fase: "+Core.getPhase()+" Cartas: "+Core.getPlayer(Core.getLocal()).size());
                            
                            Core.setCounter(0);
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
                        //Core.setActual(Core.getLocal());
                                                
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
                        
                    }else if(Core.getPhase()==Utils.PHASE_GAME){
                        //Carta a la mano en fase de juego
                        //hay 2 casos para que esto ocurra.
                        if(Core.getGrabCase() == 0){
                            //que no tenga una carta para jugar y acabo de recibir una
                            if(!Core.isLocalAbleToPlay()){
                                //si con la nueva carta no puedo jugar debo pasar el turno con carta null;
                                System.out.println("PASO EL TURNO");
                                
                                from = Utils.intToBinary(Core.getLocal(), 2);
                                Core.nextTurn();
                                to = Utils.intToBinary(Core.getActual(), 2);

                                instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                                control = from+to+instruction;

                                direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                                color = Utils.intToBinary(Utils.colorToInt(Utils.Color.VERDE), 2);
                                card = Utils.intToBinary(Utils.valueToInt(Utils.Value.NONE), 4);
                                info = direction+color+card;
                                System.out.println("INFOS:"+info);

                                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                                SerialComm.sendTrama(trama);
                            }
                            Core.setGrabCase(-1);
                        }else if(Core.getGrabCase() == 1){
                            //que tenga que agarrar cartas por +2 0 +4
                            if(Core.getCounter() > 0){
                                //se obtiene la siguiente carta
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
                                
                                //se disminuye en uno el contador
                                Core.setCounter(Core.getCounter()-1);
                            }else{
                                //se pasa el turno cuando se agarran todas las cartas
                                System.out.println("PASO EL TURNO");
                                
                                from = Utils.intToBinary(Core.getLocal(), 2);
                                Core.nextTurn();
                                to = Utils.intToBinary(Core.getActual(), 2);

                                instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                                control = from+to+instruction;

                                direction = "1"+Utils.intToBinary(Core.getDirection(), 1);
                                color = Utils.intToBinary(Utils.colorToInt(Utils.Color.VERDE), 2);
                                card = Utils.intToBinary(Utils.valueToInt(Utils.Value.NONE), 4);
                                info = direction+color+card;
                                System.out.println("INFOS:"+info);

                                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                                SerialComm.sendTrama(trama);
                                
                                Core.setGrabCase(-1);
                            }
                        }
                        
                    }
                }
            }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_CARD_PLAY){
                //si se recibe una instruccion de carta a la mesa
                if(Core.getPhase()==Utils.PHASE_BOARD){
                    //en caso de no estar en fase de juego se inicia
                    Core.setPhase(Utils.PHASE_GAME);
                    MainViewController.setInfoLabel("Fase: "+Core.getPhase()+" Cartas: "+Core.getPlayer(Core.getLocal()).size());
                    System.out.println("Fase: "+Core.getPhase()+" Cartas: "+Core.getPlayer(Core.getLocal()).size());
                    //en este punto se debe cambiar de ventana al tablero de juego
                    
                    //CAMBIO A TABLERO DE JUEGO
                    MainViewController.showGameView();
                }
                
                //se asigna el turno al destino
                //Core.setActual(Utils.binaryToInt(to));
                
                if(Utils.valueToInt(Utils.Value.NONE) != Utils.binaryToInt(card)){
                    //si la carta recibida es distinta de null (paso de turno)
                    
                    //se suma uno al contador de cartas jugadas
                    Core.setCardsPlayed(Core.getCardsPlayed()+1);

                    //se coloca la carta en la mesa;
                    newCard = null;
                    while(newCard==null){
                        newCard = Core.getPlayer(Utils.binaryToInt(from)).getCard(Utils.intToColor(Utils.binaryToInt(color)),Utils.intToValue(Utils.binaryToInt(card)));
                    }
                    Core.getDrop().addCard(newCard);

                    //se setea color en la mesa que llega en trama
                    Core.setTableColor(Utils.binaryToInt(color));

                    //se actualiza la mesa
                    GameViewController.updateTable();
                }
                
                //si yo no envie el mensaje
                if(Core.getLocal()!=Utils.binaryToInt(from)){
                    //se retransmite el mensaje y se coloca la carta en la mesa
                    SerialComm.sendTrama(trama);
                    
                    try {
                        Thread.sleep(Utils.SLEEP_TIME);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                                            
                    if(Core.getActual()==Core.getLocal()){
                        //si es mi turno
                        if( (Utils.binaryToInt(card) == Utils.valueToInt(Utils.Value.MAS_DOS) || Utils.binaryToInt(card) == Utils.valueToInt(Utils.Value.MAS_CUATRO)) && Core.getCardsPlayed()!= 0 ){
                            //si se debe agarrar cartas se setea el caso de agarre solo en casos distintos a primera carta a la mesa
                            Core.setGrabCase(1);
                            
                            if(Utils.binaryToInt(card) == Utils.valueToInt(Utils.Value.MAS_DOS)){
                                //si es una carta +2
                                Core.setCounter(1);
                            }else if(Utils.binaryToInt(card) == Utils.valueToInt(Utils.Value.MAS_CUATRO)){
                                Core.setCounter(3);
                            }
                            
                            //se obtiene la siguiente carta
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
                            

                        }                        
                    }
                    
                    
                }else{
                    //si yo envie el mensaje
                    //no se retransmite solo se coloca carta en la mesa
                                        
                    //System.out.println("YO ENVIE EL MENSAJE Y PUSE LA CARTA EN LA MESA");
                    //System.out.flush();
                    
                    //si es mi turno (solo sucede si soy JUGADOR INICIAL o JUGADOR GANADOR)
                    //y ya no tengo cartas, soy ganador
                    if(Core.getActual()==Core.getLocal() && Core.getPlayer(Core.getLocal()).size() == 0){
                        System.out.println("GANE!!!!");
                        
                        //ENVIAR A TODOS MENSAJE DE VICTORIA
                        from = Utils.intToBinary(Core.getLocal(), 2);
                        to = from;
                        instruction = Utils.intToBinary(Utils.CONTROL_VICTORY, 4);
                        control = from+to+instruction;

                        //construccion de segmento informacion
                        info = Utils.INFO_FILLER_8;
                        System.out.println("INFOS:"+info);

                        //envio de la primera trama
                        trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                        SerialComm.sendTrama(trama);
                        
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
                //Core.printTable();
                System.out.println("TURNO ACTUAL: "+Utils.binaryToInt(to));
                System.out.println("Contador: "+Core.getCounter()+" Jugadas: "+Core.getCardsPlayed());
                System.out.println("Color: "+Utils.intToColor(Core.getTableColor()));
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
        }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS){
            System.out.println(from+" "+to+" "+instruction);
            
            //si recibo mensaje de cartas iniciales y soy jugador distinto de cero
            if(Utils.binaryToInt(instruction)==Utils.CONTROL_STARTING_CARDS&&Core.getLocal()!=0){
                //Core.setActual(Core.getLocal());
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
                //Core.setActual(Core.getLocal());
         
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
        }else if(Utils.binaryToInt(instruction)==Utils.CONTROL_VICTORY){
            //si no soy origen y dentino debo retransmitir y debo desabilitar las interfaces
            //debo anunciar al ganador
            //y terminar ejecucion.
            Core.setPhase(Utils.PHASE_VICTORY);
            if(Core.isLocalTurn()){
                System.out.println("GANE LA PARTIDA!!!");
            }else{
                System.out.println("El Jugador "+Core.getActual()+" GANO!!");
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
    
    public static void playCard(Card cardToPlay){
        Trama trama =  null;
        
        if(Core.isLocalTurn()){
            //Si es turno local puedo jugar carta si cumple con condicion de carta previa
             
            if(!cardToPlay.getColor().equals(Utils.Color.NONE)){
                //Si el color de la carta a jugar es distinto de NONE                    
                if(cardToPlay.getColor().equals(Utils.intToColor(Core.getTableColor())) || cardToPlay.getValue().equals(Core.getDrop().showLastCard().getValue()) || (Core.getDrop().showLastCard().getValue().equals(Utils.Value.CAMBIA_COLOR) && Core.getCardsPlayed() == 0)){
                    //si el color o el valor son iguales a la mesa puedo jugar
                    //paso a formar trama al recibir este mensaje se quita de la mano
                    from = Utils.intToBinary(Core.getLocal(), 2);

                    //si la carta es CAMBIA_SENTIDO o PIERDE TURNO se setea el turno que sigue
                    //y no es la ultima carta en la mano
                    if(Core.getPlayer(Core.getLocal()).size()>1){
                        if(cardToPlay.getValue().equals(Utils.Value.CAMBIA_SENTIDO)){
                            Core.changeDirection();
                            Core.nextTurn();
                        }else if(cardToPlay.getValue().equals(Utils.Value.PIERDE_TURNO)){
                            Core.nextTurn();
                            Core.nextTurn();
                        }
                        else{
                            Core.nextTurn();
                        }
                        to = Utils.intToBinary(Core.getActual(), 2);
                    }else{
                        to = Utils.intToBinary(Core.getLocal(), 2);
                    }
                    
                    
                    instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                    control = from+to+instruction;

                    direction = "1"+Utils.intToBinary(Core.getDirection(), 1);

                    color = Utils.intToBinary(Utils.colorToInt(cardToPlay.getColor()), 2);
                    card = Utils.intToBinary(Utils.valueToInt(cardToPlay.getValue()), 4);
                    info = direction+color+card;
                    System.out.println("INFOS:"+info);


                    trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                    SerialComm.sendTrama(trama);
                }
            }else{
                //si el color de la carta es igual a NONE
                //paso a formar trama al recibir este mensaje se quita de la mano
                from = Utils.intToBinary(Core.getLocal(), 2);

                //el turno es del siguiente en el mismo sentido
                //si no es la ultima carta.
                if(Core.getPlayer(Core.getLocal()).size()>1){
                    Core.nextTurn();
                    to = Utils.intToBinary(Core.getActual(), 2);
                }else{
                    to = Utils.intToBinary(Core.getLocal(), 2);
                }
                
                instruction = Utils.intToBinary(Utils.CONTROL_CARD_PLAY, 4);
                control = from+to+instruction;

                direction = "1"+Utils.intToBinary(Core.getDirection(), 1);

                //DEBO SELECCIONAR COLOR CON UNA VENTANA ADICIONAL ANTES DE ENVIAR COLOR
                //mientras no elijo un color
                int selectedColor = -1;
                while(selectedColor == -1)
                    selectedColor = Utils.colorChooser(Utils.COLOR_DIALOG, null);
                    
                color = Utils.intToBinary(selectedColor, 2);

                card = Utils.intToBinary(Utils.valueToInt(cardToPlay.getValue()), 4);
                info = direction+color+card;
                System.out.println("INFOS:"+info);


                trama = new Trama(Utils.binaryToInt(control), Utils.binaryToInt(info));
                SerialComm.sendTrama(trama);
            }
            
        }
    }
    
    public static void takeCardToHand(){
        //se obtiene la carta en la mano
        if(Core.isLocalTurn()){
            Trama trama;

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
        }
    }
}
