package agentes;
import agentesc.Contenedor;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
public class Agente1 extends Agent {
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

    class Comportamiento extends Behaviour {
        boolean terminado = false;
        int check = 0;
        @Override
        public void action(){
            System.out.println("Agente 1: ");
            ACLMessage msj = blockingReceive();         //this is blocking it till it recieves a message
            String idC = msj.getConversationId();
            if((idC.equalsIgnoreCase("COD0301"))&&(check==1)){
                Mensajes.enviar(ACLMessage.INFORM, "BuscarDatos",  "yes", "COD0103", getAgent());
                ACLMessage acl = blockingReceive();
                System.out.println("Agent 1 -> 3");
                check=2;
            }
            if((idC.equalsIgnoreCase("COD0301"))&&check==0){
                Mensajes.enviar(ACLMessage.INFORM, "BuscarDatos",  "yes", "COD0102", getAgent());
                ACLMessage acl = blockingReceive();
                System.out.println("Agent 1 -> 2");
                check=1;
            }
            if (check==2){
                doDelete();
                System.out.println("Agente 1 terminado");
            }
        }
        @Override
        public boolean done() {
            //control de acciones al agente
            return terminado;
        }
    }
}
