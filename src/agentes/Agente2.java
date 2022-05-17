package agentes;

import agentesc.Contenedor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jdk.jshell.MethodSnippet;
import java.util.logging.Level;

public class Agente2 extends Agent {
    @Override
    protected void setup() {                //crea el primer hilo en la creacion del agente
        addBehaviour(new Comportamiento());
    }
    @Override
    protected void takeDown(){          //last wishes
        Contenedor c = (Contenedor)getArguments()[0];
        int i = Integer.parseInt(getArguments()[1].toString());
        i++;
        c.crearHijos("AgenteHijo"+i, new Object[]{c, i});
        System.out.println("Morir");
    }
    class Comportamiento extends CyclicBehaviour {
        int check=0;

        @Override
        public void action(){
            //todo lo que necesite hacer el agente
            //ANN, AG, Bayes, if else
            System.out.print("Agente 2: ");
            ACLMessage msj = blockingReceive();         //this is blocking it till it recieves a message
            String idC = msj.getConversationId();

            if(idC.equalsIgnoreCase("COD0102")) {
                String estado = msj.getContent();
                if (estado == "yes") {
                    System.out.println("Mensaje de 1 a 2");
                    check=1;
                }
            }
            else if ((idC.equalsIgnoreCase("COD0302"))&&(check==1)) {
                System.out.println("Mensaje de 3 a 2");
                String confrimacion = msj.getContent();
                if (confrimacion.equalsIgnoreCase("yes")) {
                    System.out.println("Ciclo completo");
                }
                else{
                    System.out.println("Ciclo incompleto");
                }
                Mensajes.enviar(ACLMessage.INFORM, "Ag3", "Completar ciclo", "COD0203", getAgent());
                check=2;
            }
            if(check==2){
                //ciclo completo, crear nuevos hijos y matar padres
                System.out.println("Agent 2 finished");
                doDelete();
            }
        }
    }
}
