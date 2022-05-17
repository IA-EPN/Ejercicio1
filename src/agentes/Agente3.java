package agentes;

import agentesc.Contenedor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;


public class Agente3 extends Agent {
    @Override
    protected void setup() {                //crea el primer hilo en la creacion del agente
        addBehaviour(new Comportamiento());
    }
//    @Override
//    protected void takeDown(){
//        Contenedor c = (Contenedor)getArguments()[0];
//        int i = Integer.parseInt(getArguments()[1].toString());
//        i++;
//        c.crearHijos("AgenteHijo"+i, new Object[]{c, i});
//        System.out.println("Morir");
//    }

    class Comportamiento extends CyclicBehaviour {
        boolean terminado = false;
        int check = 0;
        @Override
        public void action(){
            //todo lo que necesite hacer el agente
            System.out.print("Agente 3: ");
            ACLMessage msj = blockingReceive();         //this is blocking it till it recieves a message
            String idC = msj.getConversationId();
            //doDelete(); //matar el agente
            //antes hacer algo entonces usar un comportamiento del agente
            //se establece coneccion entre 1 y 3
            if (idC.equalsIgnoreCase("COD0103")) {
                Mensajes.enviar(ACLMessage.INFORM, "BuscarDatos",  "yes", "COD0301", getAgent());
                check=1;
            }
            if ((idC.equalsIgnoreCase("COD0203"))&&(check==1)) {
                Mensajes.enviar(ACLMessage.INFORM, "BuscarDatos",  "yes", "COD0302", getAgent());
                check=2;
            }
            if(check==2){
                System.out.println("Agent 3 finished");
                doDelete();
            }
        }
    }
}
