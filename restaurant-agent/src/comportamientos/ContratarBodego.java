package comportamientos;

import util.Mensaje;
import util.Performativas;
import agentes.AgenteProveedor;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

public class ContratarBodego extends MsgReceiver{

	private static final long serialVersionUID = -4902283519265681261L;
	AgenteProveedor miAgente;
	
	public ContratarBodego(AgenteProveedor a, String id, long deadline){
		//Agent, MessageTemplate, deadline, DataStore, msgKey
		super(a,MessageTemplate.MatchConversationId(id),System.currentTimeMillis() + deadline,null,null);
		miAgente=a;
	}

	protected void handleMessage(ACLMessage msg) {
		if(msg==null){
			Mensaje.mandaMensaje(miAgente, Performativas.INFORMAR, miAgente.agentesBodegos, miAgente.ingredientePorAcomodar.clave);
			myAgent.addBehaviour(new ContratarBodego(miAgente,"Paquete "+miAgente.idConversacion,2000L));
		}else{
			Mensaje.mandaMensaje(miAgente, Performativas.CONFIRMAR, msg.getSender(), miAgente.ingredientePorAcomodar.clave); 
			miAgente.ingredientePorAcomodar=null;
		}
	}
}
